package com.uni.eventbridge.presentation.eventDetails

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event

fun Event.toUiState() = EventDetailsUiState(
    id = id,
    heroImageUrl = bannerUrl,
    category = category.toUiState(),
    title = name,
    organizer = organizer,
    date = date,
    startTime = startTime,
    endTime = endTime,
    venueName = location,
    venueDetail = venueDetail,
    description = description,
    maxAttendees = maxAttendees,
    remainingSeats = remainingSeats,
    pinLatitude = latitude,
    pinLongitude = longitude,
    )

fun Category.toUiState() = EventDetailsUiState.CategoryUiState(
    id = id,
    name = name
)