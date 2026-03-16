package com.uni.eventbridge.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.designSystem.theme.Primary
import com.uni.eventbridge.domain.model.Location
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.layers.LineLayer
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.style.rememberStyleState
import org.maplibre.spatialk.geojson.Position

@Composable
fun NavigationMapView(
    userLocation: Location?,
    eventLocation: Location,
    route: List<Location>,
    isLoading: Boolean,
) {
    val initialTarget = userLocation ?: eventLocation

    val cameraState = rememberCameraState(
        firstPosition = CameraPosition(
            target = Position(
                longitude = initialTarget.longitude,
                latitude = initialTarget.latitude
            ),
            zoom = 17.5
        )
    )
    val styleState = rememberStyleState()

    LaunchedEffect(userLocation) {
        userLocation?.let {
            cameraState.animateTo(
                CameraPosition(
                    target = Position(longitude = it.longitude, latitude = it.latitude),
                    zoom = 17.5
                )
            )
        }
    }

    val routeGeoJson = remember(route) {
        if (route.size < 2) return@remember null
        val coords = route.joinToString(",") { "[${it.longitude},${it.latitude}]" }
        GeoJsonData.JsonString("""{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"LineString","coordinates":[$coords]}}]}""")
    }

    val currentUserLocation by rememberUpdatedState(userLocation)

    val userGeoJson = if (currentUserLocation != null) {
        GeoJsonData.JsonString(
            """{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[${currentUserLocation!!.longitude},${currentUserLocation!!.latitude}]}}]}"""
        )
    } else null


    val eventGeoJson = remember(eventLocation) {
        GeoJsonData.JsonString("""{"type":"FeatureCollection","features":[{"type":"Feature","geometry":{"type":"Point","coordinates":[${eventLocation.longitude},${eventLocation.latitude}]}}]}""")
    }

    Box(Modifier.fillMaxSize()) {

        MaplibreMap(
            modifier = Modifier.fillMaxSize(),
            cameraState = cameraState,
            styleState = styleState,
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
            options = MapOptions(),
        )
        {
            // Route line
            if (routeGeoJson != null) {
                val routeSource = rememberGeoJsonSource(data = routeGeoJson)
                LineLayer(
                    id = "route-layer",
                    source = routeSource,
                    color = const(Color(0xFF3463EF)),
                    width = const(5.dp),
                    opacity = const(1f),
                )
            }

            if (userGeoJson != null) {
                val userSource = rememberGeoJsonSource(data = userGeoJson)
                CircleLayer(
                    id = "user-layer",
                    source = userSource,
                    radius = const(10.dp),
                    color = const(Color(0xFF00C853)),
                    strokeWidth = const(3.dp),
                    strokeColor = const(Color.White),
                )
            }

            val eventSource = rememberGeoJsonSource(data = eventGeoJson)
            CircleLayer(
                id = "event-layer",
                source = eventSource,
                radius = const(10.dp),
                color = const(Color(0xFFD50000)),
                strokeWidth = const(3.dp),
                strokeColor = const(Color.White),
            )
        }

        if (isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary)
            }
        }
    }
}