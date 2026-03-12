package com.uni.eventbridge.presentation.eventDetails

sealed interface EventDetailsUIEffect {
    data object NavigateBack : EventDetailsUIEffect
    data object ShowJoinSuccessSnakeBar : EventDetailsUIEffect
//    data class OpenInMaps(
//        val latitude: Double?,
//        val longitude: Double?,
//        val venueName: String,
//        val venueDetail: String,
//    ) : EventDetailsUIEffect
}