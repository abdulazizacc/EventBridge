package com.uni.eventbridge.presentation.eventDetails

sealed interface EventDetailsUIEffect {
    data object NavigateBack : EventDetailsUIEffect
    data object ShowJoinSuccessSnakeBar : EventDetailsUIEffect
    data object ShowLeaveSuccessSnackBar : EventDetailsUIEffect
    data class ShowErrorSnackBar(val message: String) : EventDetailsUIEffect
    data class OpenNavigationMap(val lat: Double, val lon: Double) : EventDetailsUIEffect
}