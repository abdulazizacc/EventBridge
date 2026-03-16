package com.uni.eventbridge.presentation.map

import NavigationUiState
import com.uni.eventbridge.domain.model.Location
import com.uni.eventbridge.domain.repository.LocationRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class NavigationViewModel(
    private val locationRepository: LocationRepository,
    private val eventLat: Double,
    private val eventLon: Double,
) : BaseViewModel<NavigationUiState, Unit>(NavigationUiState()) {

    init {
        loadRoute()
        observeLocation()
    }

    private fun loadRoute() {
        tryToExecute(
            callee = {
                val userLocation = locationRepository.getCurrentLocation()
                locationRepository.getRoute(
                    origin = userLocation,
                    destination = Location(eventLat, eventLon)
                )
            },
            onStart = { updateState { it.copy(isLoading = true) } },
            onSuccess = { route ->
                updateState {
                    it.copy(isLoading = false, route = route, userLocation = route.origin)
                }
            },
            onError = { error ->
                updateState { it.copy(isLoading = false) }
            }
        )
    }

    private fun observeLocation() {
        tryToCollect(
            flowProvider = {
                locationRepository.getLocationUpdates()
            },
            onNewValue = { location ->
                updateState {
                    it.copy(userLocation = location)
                }
            }
        )

    }
}