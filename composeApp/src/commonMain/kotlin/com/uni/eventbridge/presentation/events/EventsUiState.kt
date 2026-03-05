package com.uni.eventbridge.presentation.events

data class EventsUiState(
    val isLoading: Boolean = false,
    val events: List<EventUiState> = emptyList(),
) {
    data class EventUiState(
        val id: Long,
        val title: String,
        val date: String,
        val location: String,
        val imageUrl: String,
    )
}