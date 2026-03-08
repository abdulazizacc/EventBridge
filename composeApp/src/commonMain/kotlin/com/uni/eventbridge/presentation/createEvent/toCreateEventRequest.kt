package com.uni.eventbridge.presentation.createEvent

import com.uni.eventbridge.domain.model.CreateEventDraft

fun CreateEventUiState.toCreateEventRequest() = CreateEventDraft(
    title = basicInfo.title,
    category = basicInfo.category,
    department = basicInfo.department,
    bannerBytes = basicInfo.bannerBytes,
    date = dateAndLocation.date,
    startTime = dateAndLocation.startTime,
    endTime = dateAndLocation.endTime,
    location = dateAndLocation.location,
    pinLatitude = dateAndLocation.pinLatitude,
    pinLongitude = dateAndLocation.pinLongitude,
    description = basicInfo.description
)