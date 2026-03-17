package com.uni.eventbridge.presentation.createEvent

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.model.CreateEventDraft

fun CreateEventUiState.toCreateEventRequest() = CreateEventDraft(
    title = basicInfo.title.orEmpty(),
    categoryId = basicInfo.categoryId,
    department = basicInfo.department.orEmpty(),
    bannerBytes = basicInfo.bannerBytes,
    date = dateAndLocation.date.orEmpty(),
    startTime = dateAndLocation.startTime.orEmpty(),
    endTime = dateAndLocation.endTime.orEmpty(),
    location = dateAndLocation.location.orEmpty(),
    pinLatitude = dateAndLocation.pinLatitude,
    pinLongitude = dateAndLocation.pinLongitude,
    description = basicInfo.description.orEmpty(),
    organizerId = 0L,
)

fun Category.toUiState() = CreateEventUiState.CategoryUiState(
    id = id,
    name = name,
)
