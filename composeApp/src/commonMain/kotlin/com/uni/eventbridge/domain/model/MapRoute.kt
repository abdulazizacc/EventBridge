package com.uni.eventbridge.domain.model

data class MapRoute(
    val origin: Location,
    val destination: Location,
    val distanceMeters: Double,
    val durationSeconds: Double,
    val polyline: List<Location>
)