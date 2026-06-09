package com.uni.eventbridge.data.repository

import com.uni.eventbridge.data.mapper.toDomain
import com.uni.eventbridge.data.remote.dto.CategoryDto
import com.uni.eventbridge.data.remote.dto.EventDto
import com.uni.eventbridge.data.remote.dto.MembershipInsert
import com.uni.eventbridge.data.remote.dto.ProfileDto
import com.uni.eventbridge.data.remote.dto.toDomain
import com.uni.eventbridge.data.remote.supabase
import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.entity.User
import com.uni.eventbridge.domain.model.CreateEventDraft
import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.domain.util.PagedResult
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.time.Clock

class SupabaseEventRepository : EventRepository {

    override suspend fun getCategory(): List<Category> {
        return supabase.postgrest
            .rpc("get_categories")
            .decodeList<CategoryDto>()
            .map { it.toDomain() }
    }

    override suspend fun getEventDetailsById(eventId: Long): Event =
        supabase.postgrest.rpc(
            "get_event_by_id",
            mapOf("p_event_id" to eventId)
        ).decodeList<EventDto>()
            .first()
            .toDomain()

    override suspend fun getEventByCategory(
        categoryId: Long?,
        page: Int,
        pageSize: Int,
    ): PagedResult<Event> {
        val from = (page * pageSize).toLong()
        val to = (from + pageSize - 1)

        val items = supabase.postgrest.rpc(
            "get_events_by_category",
            buildMap { put("p_category_id", categoryId) }
        ) {
            range(from, to)
        }.decodeList<EventDto>().map { it.toDomain() }
        return PagedResult(
            items = items,
            currentPage = page,
            pageSize = pageSize
        )
    }

    override suspend fun getEventBySearch(query: String): List<Event> =
        searchEvent(query)

    override suspend fun searchEvent(query: String): List<Event> {
        return supabase.postgrest.rpc(
            "search_events",
            mapOf("query" to query)
        ).decodeList<EventDto>().map { it.toDomain() }
    }


    override suspend fun joinEvent(eventId: Long) {
        val userId = supabase.auth.currentUserOrNull()?.id ?: return
        try {
            supabase.postgrest["memberships"]
                .insert(MembershipInsert(eventId, userId))
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun leaveEvent(eventId: Long) {
        val userId = supabase.auth.currentUserOrNull()?.id ?: return
        supabase.postgrest["memberships"]
            .delete {
                filter {
                    eq("event_id", eventId)
                    eq("user_id", userId)
                }
            }
    }

    override suspend fun getMyEvents(): List<Event> {
        return try {
            val user = supabase.auth.currentUserOrNull()

            val result = supabase.postgrest
                .rpc("get_my_events")
                .decodeList<EventDto>()
            result.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun isUserJoined(eventId: Long): Boolean {
        val userId = supabase.auth.currentUserOrNull()?.id ?: return false
        val result = supabase.postgrest["memberships"]
            .select {
                filter {
                    eq("event_id", eventId)
                    eq("user_id", userId)
                }
                limit(1)
            }
            .decodeList<MembershipInsert>()
        return result.isNotEmpty()
    }

    override suspend fun createEvent(request: CreateEventDraft) {
        val userId = supabase.auth.currentUserOrNull()?.id ?: return

        val bannerUrl = request.bannerBytes?.let { bytes ->
            val path = "banners/${userId}/${Clock.System.now()}.jpg"
            supabase.storage["event-banners"].upload(path, bytes)
            supabase.storage["event-banners"].publicUrl(path)
        } ?: ""
        try {
            val params = buildJsonObject {
                put("p_name", request.title)
                put("p_description", request.description)
                put("p_banner_url", bannerUrl)
                put("p_location", request.location)
                put("p_date", request.date)
                put("p_time", request.startTime)
                put("p_category_id", request.categoryId)
                put("p_end_time", request.endTime)
                put("p_department", request.department)
                if (request.pinLatitude != null) put("p_pin_latitude", request.pinLatitude)
                if (request.pinLongitude != null) put("p_pin_longitude", request.pinLongitude)
            }
            supabase.postgrest.rpc("create_event", params)

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getEventAttendance(eventId: Long): List<User> {
        return supabase.postgrest.rpc(
            "get_event_attendees",
            mapOf("p_event_id" to eventId)
        ).decodeList<ProfileDto>().map { it.toDomain() }

    }
}
