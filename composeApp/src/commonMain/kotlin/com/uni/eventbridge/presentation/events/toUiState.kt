package com.uni.eventbridge.presentation.events

import com.uni.eventbridge.domain.entity.Event

fun Event.toUiState() = EventsUiState.EventUiState(
    id = id,
    title = name,
    date = "$date • $time",
    location = location,
    imageUrl = bannerUrl,
)