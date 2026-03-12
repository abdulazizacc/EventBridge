package com.uni.eventbridge.presentation.search

sealed interface SearchEffect {
    data class NavigateToEventDetail(val eventId: Long) : SearchEffect
}