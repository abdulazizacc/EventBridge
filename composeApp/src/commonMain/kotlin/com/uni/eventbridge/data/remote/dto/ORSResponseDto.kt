package com.uni.eventbridge.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrsRouteResponse(
    val routes: List<OrsRoute>
)

@Serializable
data class OrsRoute(
    val summary: OrsSummary,
    val geometry: String,
    @SerialName("way_points") val wayPoints: List<Int>
)

@Serializable
data class OrsSummary(
    val distance: Double,
    val duration: Double
)