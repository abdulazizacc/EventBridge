package com.uni.eventbridge.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.koin.java.KoinJavaComponent.inject


actual fun getDatabaseBuilder(): RoomDatabase.Builder<EventBridgeDatabase> {
    val context: Context by inject(Context::class.java)
    val dbFile = context.getDatabasePath("eventbridge.db")
    return Room.databaseBuilder(
        context = context,
        name = dbFile.absolutePath
    )
}

actual object EventBridgeDatabaseConstructor : RoomDatabaseConstructor<EventBridgeDatabase> {
     actual override fun initialize(): EventBridgeDatabase {
        error("Android uses Room.databaseBuilder")
    }
}