package com.uni.eventbridge.presentation.home

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event

fun Event.toUiState() = HomeUiState.EventUiState(
    id = id,
    title = name,
    description = description,
    bannerUrl = bannerUrl,
    location = location,
    date = date,
    category = category.toUiState(),
    remainingSeats = remainingSeats,

)

fun Category.toUiState() = HomeUiState.CategoryUiState(
    id = id,
    name = name
)