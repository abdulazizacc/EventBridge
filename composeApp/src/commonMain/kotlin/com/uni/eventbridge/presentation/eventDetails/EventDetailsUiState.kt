package com.uni.eventbridge.presentation.eventDetails

import com.uni.eventbridge.domain.model.Location
import com.uni.eventbridge.domain.model.MapRoute

data class EventDetailsUiState(
    val id: Long = 0,
    val isLoading: Boolean = false,
    val heroImageUrl: String? = null,
    val category: CategoryUiState = CategoryUiState(),
    val title: String? = null,
    val organizer: String? = null,
    val organizerAvatarUrl: String? = null,
    val date: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val venueName: String? = null,
    val venueDetail: String? = null,
    val mapImageUrl: String? = null,
    val description: String? = null,
    val maxAttendees: Int? = null,
    val remainingSeats: Int? = null,
    val isRegistered: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isFull: Boolean = false,
    val snackbarMessage: String? = null,
    val pinLatitude: Double? = null,
    val pinLongitude: Double? = null,
    val userLocation: Location? = null,
    val route: MapRoute? = null,
) {
    data class CategoryUiState(
        val id: Long = 0L,
        val name: String = ""
    )
}