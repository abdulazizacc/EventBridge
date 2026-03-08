package com.uni.eventbridge.data.repository

import EventRemoteDataSource
import com.uni.eventbridge.data.mapper.toDomain
import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.model.CreateEventDraft
import com.uni.eventbridge.domain.repository.EventRepository

class EventRepositoryImpl (
    private val remoteDataSource: EventRemoteDataSource
) : EventRepository {

    override suspend fun getCategory(): List<Category> =
        remoteDataSource.getCategory().map { it.toDomain() }

    override suspend fun getEventDealsById(eventId: Long): Event =
        remoteDataSource.getEventDeals(eventId).toDomain()

    override suspend fun getEventByCategory(categoryId: Long?): List<Event> {
        return remoteDataSource.getEventByCategory(categoryId).map { it.toDomain() }
    }

    override suspend fun getEventBySearch(query: String): List<Event> =
        remoteDataSource.getEventBySearch(query).map { it.toDomain() }

    override suspend fun joinEvent(eventId: Long) {
        remoteDataSource.joinEvent(eventId)
    }
    override suspend fun getMyEvents(): List<Event> =
        remoteDataSource.getMyEvents().map { it.toDomain() }

    override suspend fun createEvent(request: CreateEventDraft) {
        remoteDataSource.createEvent(request)
    }

    override suspend fun searchEvent(query: String): List<Event> {
        return emptyList()
    }
}