package com.uni.eventbridge.data.mapper

import com.uni.eventbridge.data.remote.dto.OrsRouteResponse
import com.uni.eventbridge.domain.model.MapRoute
import com.uni.eventbridge.util.decodePolyline

fun OrsRouteResponse.toDomain(): MapRoute {
    val route = routes.first()
    val polyline = decodePolyline(route.geometry)
    return MapRoute(
        origin = polyline.first(),
        destination = polyline.last(),
        distanceMeters = route.summary.distance,
        durationSeconds = route.summary.duration,
        polyline = polyline
    )
}