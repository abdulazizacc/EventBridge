package com.uni.eventbridge

import com.uni.eventbridge.domain.model.Location
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual class PlatformLocationProvider {

    private val locationManager = CLLocationManager()

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getLocation(): Location {

        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestWhenInUseAuthorization()

        val loc = locationManager.location
            ?: error("Location unavailable")

        val coordinate = loc.coordinate

        return coordinate.useContents {
            Location(
                latitude = latitude,
                longitude = longitude
            )
        }
    }

    actual fun getLocationFlow(): Flow<Location> {
        return emptyFlow()
    }
}