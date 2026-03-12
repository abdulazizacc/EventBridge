package com.uni.eventbridge.data.local

import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

expect fun getDatabaseBuilder(): RoomDatabase.Builder<EventBridgeDatabase>

fun buildDatabase(builder: RoomDatabase.Builder<EventBridgeDatabase>): EventBridgeDatabase {
    return builder
        .fallbackToDestructiveMigration(true)
        .build()
}
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object EventBridgeDatabaseConstructor : RoomDatabaseConstructor<EventBridgeDatabase>

