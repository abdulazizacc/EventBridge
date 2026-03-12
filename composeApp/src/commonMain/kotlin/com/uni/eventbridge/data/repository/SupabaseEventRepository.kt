package com.uni.eventbridge.data.repository

import com.uni.eventbridge.data.mapper.toDomain
import com.uni.eventbridge.data.mapper.toDto
import com.uni.eventbridge.data.remote.dto.CategoryDto
import com.uni.eventbridge.data.remote.dto.EventDto
import com.uni.eventbridge.data.remote.dto.MembershipInsert
import com.uni.eventbridge.data.remote.supabase
import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.model.CreateEventDraft
import com.uni.eventbridge.domain.repository.EventRepository
import io.github.aakira.napier.Napier
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import io.github.jan.supabase.storage.storage
import kotlin.time.Clock

class SupabaseEventRepository : EventRepository {

    override suspend fun getCategory(): List<Category> {
        val result = supabase.postgrest["categories"].select()
        Napier.d (tag = "CategoryProblem", message = "Raw result from Supabase:  ${result.data}")

        return result.decodeList<CategoryDto>().map { it.toDomain() }
    }

    override suspend fun getEventDetailsById(eventId: Long): Event =
        supabase.postgrest.rpc(
            "get_event_by_id",
            mapOf("p_event_id" to eventId)
        ).decodeList<EventDto>()
            .first ()
            .toDomain()

    override suspend fun getEventByCategory(categoryId: Long?): List<Event> =
        supabase.postgrest.rpc(
            "get_events_by_category",
            buildMap { put("p_category_id", categoryId) }
        ).decodeList<EventDto>().map { it.toDomain() }

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
        Napier.d(tag = "joinEvent", message = "Attempting join: eventId=$eventId userId=$userId")

        try {
            supabase.postgrest["memberships"]
                .insert(MembershipInsert(eventId, userId))
            Napier.d(tag = "joinEvent", message = "Insert completed successfully")
        } catch (e: Exception) {
            Napier.e(tag = "joinEvent", message = "Insert failed: ${e.message}")
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

        val dto = request.toDto(bannerUrl)
        supabase.postgrest["events"].insert(dto)
    }
}