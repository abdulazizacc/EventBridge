package com.uni.eventbridge.presentation.eventDetails

data class EventDetailsUiState(
    val id: Long = 0,
    val isLoading: Boolean = false,
    val heroImageUrl: String = "",
    val category: CategoryUiState = CategoryUiState(),
    val title: String = "",
    val organizer: String = "",
    val organizerAvatarUrl: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val venueName: String = "",
    val venueDetail: String = "",
    val mapImageUrl: String = "",
    val description: String = "",
    val maxAttendees: Int? = null,
    val remainingSeats: Int? = null,
    val isRegistered: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isFull: Boolean = false,
    val snackbarMessage: String? = null,
) {
    data class CategoryUiState(
        val id: Long = 0L,
        val name: String = ""
    )
}