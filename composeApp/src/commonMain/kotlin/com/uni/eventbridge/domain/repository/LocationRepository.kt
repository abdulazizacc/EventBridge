package com.uni.eventbridge.domain.repository

import com.uni.eventbridge.domain.model.Location
import com.uni.eventbridge.domain.model.MapRoute
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getCurrentLocation(): Location
    suspend fun getRoute(
        origin: Location,
        destination: Location
    ): MapRoute

    fun getLocationUpdates(intervalMs: Long = 3000L): Flow<Location>

}