package com.uni.eventbridge.domain.repository

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event

interface EventRepository {
    suspend fun getCategory(): List<Category>
    suspend fun getEventDealsById(eventId:Long): Event
    suspend fun getEventByCategory(
        categoryId: Long?,
    ): List<Event>
    suspend fun getEventBySearch(
        query: String,
    ): List<Event>
    suspend fun joinEvent(eventId: Long)
    suspend fun getMyEvents(): List<Event>

}


