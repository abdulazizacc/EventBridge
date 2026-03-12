package com.uni.eventbridge.presentation.eventDetails

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event

fun Event.toUiState() = EventDetailsUiState(
    id             = id,
    title           = name,
    description    = description,
    heroImageUrl      = bannerUrl,
    venueName       = location,
    date           = date,
     startTime         = startTime,
    endTime = endTime,
    venueDetail    = venueDetail,
    category = category.toUiState(),
    maxAttendees   = maxAttendees,
    remainingSeats = remainingSeats,
    isFull         = remainingSeats != null && remainingSeats <= 0,

)

fun Category.toUiState() = EventDetailsUiState.CategoryUiState(
    id = id,
    name = name
)