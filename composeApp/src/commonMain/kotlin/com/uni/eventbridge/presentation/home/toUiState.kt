package com.uni.eventbridge.presentation.home

import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event

// data/mapper/UiStateMapper.kt
fun Event.toUiState() = HomeUiState.EventUiState(
    id = id,
    title = name,
    description = description,
    bannerUrl = bannerUrl,
    location = location,
    date = date,
    isActive = isActive,
    category = category.toUiState()
)

fun Category.toUiState() = HomeUiState.CategoryUiState(
    id = id,
    name = name
)