package com.uni.eventbridge

import android.Manifest
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.uni.eventbridge.domain.model.Location
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual class PlatformLocationProvider(
    private val fusedClient: FusedLocationProviderClient
) {

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    actual suspend fun getLocation(): Location {
        Napier.d(tag = "lolol", message = "DEBUG: getLocation() called")

        val last = fusedClient.lastLocation.await()
        Napier.d(tag = "lolol", message = "DEBUG: lastLocation = $last")

        if (last != null) {
            Napier.d(tag = "lolol", message = "DEBUG: returning lastLocation ${last.latitude}, ${last.longitude}")
            return Location(last.latitude, last.longitude)
        }

        Napier.d(tag = "lolol", message = "DEBUG: lastLocation null, trying getCurrentLocation")
        return suspendCancellableCoroutine { cont ->
            val request = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            fusedClient.getCurrentLocation(request, null)
                .addOnSuccessListener { location ->
                    Napier.d(tag = "lolol", message = "DEBUG: getCurrentLocation success = $location")
                    if (location != null) {
                        cont.resume(Location(location.latitude, location.longitude))
                    } else {
                        cont.resumeWithException(Exception("Location null after fresh request"))
                    }
                }
                .addOnFailureListener {
                    Napier.d(tag = "lolol", message = "DEBUG: getCurrentLocation failed: ${it.message}")
                    cont.resumeWithException(it)
                }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    actual fun getLocationFlow(): Flow<Location> = callbackFlow {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            3000L
        ).build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { loc ->
                    trySend(Location(loc.latitude, loc.longitude))
                }
            }
        }

        fusedClient.requestLocationUpdates(request, callback, Looper.getMainLooper())

        awaitClose {
            fusedClient.removeLocationUpdates(callback)
        }
    }
}