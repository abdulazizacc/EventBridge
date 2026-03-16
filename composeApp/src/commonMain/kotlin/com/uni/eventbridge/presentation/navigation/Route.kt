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
    @Serializable
    data object Login : Route
    @Serializable
    data object Splash : Route
    @Serializable
    data class NavigationMap(val lat: Double, val lon: Double) : Route
}
fun Route.toNavString(): String = when (this) {
    Route.Splash -> "splash"
    Route.Home -> "home"
    Route.Explore -> "explore"
    Route.Events -> "events"
    Route.Account -> "account"
    Route.Login -> "login"
    is Route.EventDetails -> "event_details/${eventId}"
    Route.CreateEvent -> "create_event"
    is Route.NavigationMap -> "navigation_map/$lat/$lon"
}
