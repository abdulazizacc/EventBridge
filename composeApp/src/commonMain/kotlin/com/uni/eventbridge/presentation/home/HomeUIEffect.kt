package com.uni.eventbridge.presentation.home

sealed interface HomeUIEffect {
    data class  NavigateToEventDetails(val eventId: Long) : HomeUIEffect
}