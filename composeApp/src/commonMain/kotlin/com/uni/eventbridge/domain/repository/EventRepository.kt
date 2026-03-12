package com.uni.eventbridge.domain.repository

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.model.CreateEventDraft

interface EventRepository {
    suspend fun getCategory(): List<Category>
    suspend fun getEventDetailsById(eventId:Long): Event
    suspend fun getEventByCategory(
        categoryId: Long?,
    ): List<Event>
    suspend fun getEventBySearch(
        query: String,
    ): List<Event>
    suspend fun joinEvent(eventId: Long)
    suspend fun leaveEvent(eventId: Long)

    suspend fun getMyEvents(): List<Event>
    suspend fun createEvent(request: CreateEventDraft)
    suspend fun isUserJoined(eventId: Long): Boolean
    suspend fun searchEvent(query: String): List<Event>

}


