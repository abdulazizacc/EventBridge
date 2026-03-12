package com.uni.eventbridge.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.java.KoinJavaComponent.inject

actual fun getDatabaseBuilder(): RoomDatabase.Builder<EventBridgeDatabase> {
    val context: Context by inject(Context::class.java)
    val dbFile = context.getDatabasePath("eventbridge.db")
    return Room.databaseBuilder(
        context = context,
        name = dbFile.absolutePath
    )
}