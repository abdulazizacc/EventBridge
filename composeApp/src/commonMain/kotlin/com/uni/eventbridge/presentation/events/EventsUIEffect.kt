package com.uni.eventbridge.presentation.events

sealed interface EventsUIEffect {
    data class NavigateToEventDetails(val eventId: Long) : EventsUIEffect
    data object NavigateToCreateEvent : EventsUIEffect
}