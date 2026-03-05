package com.uni.eventbridge.data.mapper

import com.uni.eventbridge.data.remoteData.dto.CategoryDto
import com.uni.eventbridge.data.remoteData.dto.EventDto
import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event

fun EventDto.toDomain() = Event(
    id = id,
    name = name,
    description = description,
    bannerUrl = bannerUrl,
    location = location,
    date = date,
    isActive = isActive,
    category = category.toDomain(),
    time = time,
    venueName = venueName,
    venueDetail = venueDetail,
    organizer = organizer
)

fun CategoryDto.toDomain() = Category(
    id = id,
    name = name
)