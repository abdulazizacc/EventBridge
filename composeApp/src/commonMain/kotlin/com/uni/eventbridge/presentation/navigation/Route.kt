package com.uni.eventbridge.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home : Route
    @Serializable
    data object Explore : Route
    @Serializable
    data object Events : Route
    @Serializable
    data object Account : Route
    @Serializable
    data class EventDetails(val eventId: String) : Route
    @Serializable
    data object CreateEvent : Route
}
fun Route.toNavString(): String = when (this) {
    Route.Home -> "home"
    Route.Explore -> "explore"
    Route.Events -> "events"
    Route.Account -> "account"
    is Route.EventDetails -> "event_details/$eventId"
    is Route.CreateEvent -> "create_event"
}
