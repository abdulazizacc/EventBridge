package com.uni.eventbridge.data.repository

import com.uni.eventbridge.data.local.SearchHistoryLocalDataSource
import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.model.SearchHistoryItem
import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val localDataSource: SearchHistoryLocalDataSource,
    private val remoteDataSource: EventRepository,
) : SearchRepository {

    override suspend fun searchEvents(query: String): List<Event> {
        return remoteDataSource.searchEvent(query)
    }

    override fun observeSearchHistory(): Flow<List<SearchHistoryItem>> {
        return localDataSource.observeAll()
    }

    override suspend fun saveSearchQuery(query: String) {
        if (query.isNotBlank()) {
            localDataSource.insert(query.trim())
        }
    }

    override suspend fun deleteSearchQuery(query: String) {
        localDataSource.deleteByQuery(query)
    }

    override suspend fun clearAllSearchHistory() {
        localDataSource.deleteAll()
    }
}