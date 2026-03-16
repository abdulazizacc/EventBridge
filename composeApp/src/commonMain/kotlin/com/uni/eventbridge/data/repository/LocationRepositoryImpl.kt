package com.uni.eventbridge.data.repository

import com.uni.eventbridge.PlatformLocationProvider
import com.uni.eventbridge.data.mapper.toDomain
import com.uni.eventbridge.data.remote.dto.OrsRouteResponse
import com.uni.eventbridge.domain.model.Location
import com.uni.eventbridge.domain.model.MapRoute
import com.uni.eventbridge.domain.repository.LocationRepository
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class LocationRepositoryImpl(
    private val httpClient: HttpClient,
    private val locationProvider: PlatformLocationProvider

) : LocationRepository {
    override suspend fun getCurrentLocation(): Location {
        return locationProvider.getLocation()
    }

    override suspend fun getRoute(origin: Location, destination: Location): MapRoute {
        val rawResponse = httpClient.post(
            "https://api.openrouteservice.org/v2/directions/driving-car"
        ) {
            headers {
                append(
                    "Authorization",
                    "eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6IjA0ZGE2MGM1NzgyNTRlOTZhNzdjZDBjZWE3Yjk3OWRkIiwiaCI6Im11cm11cjY0In0="
                )
                append(
                    "Accept",
                    "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8"
                )
                append("Content-Type", "application/json; charset=utf-8")
            }
            setBody("""{"coordinates":[[${origin.longitude},${origin.latitude}],[${destination.longitude},${destination.latitude}]]}""")
        }.bodyAsText()
        Napier.d(
            tag = "NavigationDebug",
            message = "raw coordinates: ${origin.longitude},${origin.latitude}],[${destination.longitude},${destination.latitude}"
        )

        Napier.d(tag = "NavigationDebug", message = "raw response: $rawResponse")

        val response: OrsRouteResponse = Json { ignoreUnknownKeys = true }
            .decodeFromString(rawResponse)

        return response.toDomain()
    }


    override fun getLocationUpdates(intervalMs: Long): Flow<Location> {
        return locationProvider.getLocationFlow()
    }
}