package com.uni.eventbridge.presentation.eventDetails

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event

fun Event.toUiState() = EventDetailsUiState(
    id = id,
    title = name,
    description = description,
    heroImageUrl = bannerUrl,
    date = date,
    category = category.toUiState(),
    timeRange = startTime,
)

fun Category.toUiState() = EventDetailsUiState.CategoryUiState(
    id = id,
    name = name
)