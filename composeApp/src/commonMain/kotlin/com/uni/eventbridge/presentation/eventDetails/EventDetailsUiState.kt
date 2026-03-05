package com.uni.eventbridge.presentation.eventDetails

data class EventDetailsUiState(
    val id: Long = 0,
    val isLoading: Boolean = false,
    val heroImageUrl: String = "",
    val category: CategoryUiState = CategoryUiState(),
    val title: String = "Annual Spring Career Fair 2024",
    val organizer: String = "Office of Student Affairs",
    val organizerAvatarUrl: String = "",
    val date: String = "October 12, 2024",
    val timeRange: String = "10:00 AM – 4:00 PM",
    val venueName: String = "Great Hall, Student Union",
    val venueDetail: String = "Main Campus, Level 2",
    val mapImageUrl: String = "",
    val description: String = "",
    val totalAttendees: Int = 45,
    val isRegistered: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
) {
    data class CategoryUiState(
        val id: Long = 0L,
        val name: String = ""
    )
}