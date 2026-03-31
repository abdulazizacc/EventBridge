package com.uni.eventbridge.presentation.events

import com.uni.eventbridge.domain.entity.Event

fun Event.toUiState() = EventsUiState.EventUiState(
    id = id,
    title = name,
    date = "$date • $startTime",
    location = location,
    imageUrl = bannerUrl,
    state = state
)