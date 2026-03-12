package com.uni.eventbridge.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(): RoomDatabase.Builder<EventBridgeDatabase> {
    val dbFile = NSHomeDirectory() + "/Documents/eventbridge.db"
    return Room.databaseBuilder<EventBridgeDatabase>(
        name = dbFile
    )
}