package com.uni.eventbridge.presentation.createEvent

sealed interface CreateEventUIEffect {
    data object NavigateBack : CreateEventUIEffect
    data object NavigateToReview : CreateEventUIEffect
    data object EventPublished : CreateEventUIEffect
}