package com.uni.eventbridge.data.local

import com.uni.eventbridge.domain.model.SearchHistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class SearchHistoryLocalDataSource(private val dao: SearchHistoryDao) {

    fun observeAll(): Flow<List<SearchHistoryItem>> {
        return dao.observeAll().map { entities ->
            entities.map { SearchHistoryItem(query = it.query, timestamp = it.timestamp) }
        }
    }

    suspend fun insert(query: String) {
        dao.upsert(
            SearchHistoryEntity(
                query = query,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        )
    }

    suspend fun deleteByQuery(query: String) = dao.deleteByQuery(query)

    suspend fun deleteAll() = dao.deleteAll()
}




