package com.uni.eventbridge.presentation.createEvent

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.model.CreateEventDraft

fun CreateEventUiState.toCreateEventRequest() = CreateEventDraft(
    title = basicInfo.title,
    categoryId = basicInfo.categoryId,
    department = basicInfo.department,
    bannerBytes = basicInfo.bannerBytes,
    date = dateAndLocation.date,
    startTime = dateAndLocation.startTime,
    endTime = dateAndLocation.endTime,
    location = dateAndLocation.location,
    pinLatitude = dateAndLocation.pinLatitude,
    pinLongitude = dateAndLocation.pinLongitude,
    description = basicInfo.description,
    organizerId = 0L,
)

fun Category.toUiState() = CreateEventUiState.CategoryUiState(
    id = id,
    name = name,
)
