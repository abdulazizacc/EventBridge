package com.uni.eventbridge.presentation.home

sealed interface HomeUIEffect {
    data class NavigateToEventDetails(val eventId: Long) : HomeUIEffect
    data object ShowJoinSuccessSnackbar : HomeUIEffect
    data object ShowLeaveSuccessSnackbar : HomeUIEffect
    data class ShowErrorSnackbar(val message: String) : HomeUIEffect
}