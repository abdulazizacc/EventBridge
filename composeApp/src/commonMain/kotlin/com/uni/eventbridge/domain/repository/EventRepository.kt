package com.uni.eventbridge.domain.repository

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event

interface EventRepository {
    suspend fun getCategory(): List<Category>
    suspend fun getEventDeals(eventId:Long): Event
    suspend fun getEventByCategory(
        categoryId: Long?,
    ): List<Event>
    suspend fun getEventBySearch(
        query: String,
    ): List<Event>
}


