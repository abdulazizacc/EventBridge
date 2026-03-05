package com.uni.eventbridge.presentation.eventDetails

sealed interface EventDetailsUIEffect {
    data object NavigateBack : EventDetailsUIEffect
    data object ShowJoinSuccessSnakeBar : EventDetailsUIEffect
}