package com.uni.eventbridge

import com.uni.eventbridge.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect class PlatformLocationProvider {
    suspend fun getLocation(): Location
    fun getLocationFlow(): Flow<Location>
}

