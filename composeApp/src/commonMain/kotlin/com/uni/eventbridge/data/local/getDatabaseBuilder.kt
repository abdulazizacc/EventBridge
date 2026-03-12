package com.uni.eventbridge.data.local

import androidx.room.RoomDatabase

expect fun getDatabaseBuilder(): RoomDatabase.Builder<EventBridgeDatabase>

fun buildDatabase(builder: RoomDatabase.Builder<EventBridgeDatabase>): EventBridgeDatabase {
    return builder
        .fallbackToDestructiveMigration(true)
        .build()
}