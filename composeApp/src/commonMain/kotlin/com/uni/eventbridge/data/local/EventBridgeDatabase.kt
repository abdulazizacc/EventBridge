package com.uni.eventbridge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchHistoryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class EventBridgeDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}