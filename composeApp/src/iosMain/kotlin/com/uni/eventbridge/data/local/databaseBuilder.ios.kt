package com.uni.eventbridge.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(): RoomDatabase.Builder<EventBridgeDatabase> {
    val dbFile = NSHomeDirectory() + "/Documents/eventbridge.db"
    return Room.databaseBuilder<EventBridgeDatabase>(
        name = dbFile
    )
}
actual object EventBridgeDatabaseConstructor :
    RoomDatabaseConstructor<EventBridgeDatabase> {
    actual override fun initialize(): EventBridgeDatabase {
        error("ios Room.databaseBuilder")
    }
}