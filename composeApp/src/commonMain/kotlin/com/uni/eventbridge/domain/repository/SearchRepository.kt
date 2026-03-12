package com.uni.eventbridge.domain.repository

import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.model.SearchHistoryItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchEvents(query: String): List<Event>
    fun observeSearchHistory(): Flow<List<SearchHistoryItem>>
    suspend fun saveSearchQuery(query: String)
    suspend fun deleteSearchQuery(query: String)
    suspend fun clearAllSearchHistory()
}