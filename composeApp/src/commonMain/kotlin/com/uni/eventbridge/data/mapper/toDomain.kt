package com.uni.eventbridge.data.mapper

import com.uni.eventbridge.data.remote.dto.CategoryDto
import com.uni.eventbridge.data.remote.dto.CreateEventDto
import com.uni.eventbridge.data.remote.dto.EventDto
import com.uni.eventbridge.domain.entity.Category
import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.model.CreateEventDraft


fun EventDto.toDomain() = Event(
    id = id,
    name = name.orEmpty(),
    description = description.orEmpty(),
    bannerUrl = bannerUrl,
    location = location.orEmpty(),
    date = date.orEmpty(),
    isActive = isActive,
    category = category?.toDomain() ?: Category(0, ""),
    startTime = startTime.orEmpty(),
    venueName = venueName.orEmpty(),
    venueDetail = venueDetail.orEmpty(),
    endTime = endTime.orEmpty(),
    organizer = organizer.orEmpty(),
    maxAttendees = maxAttendees,
    remainingSeats = remainingSeats,
    longitude = longitude,
    latitude = latitude
)

fun CategoryDto.toDomain() = Category(
    id = id,
    name = name,
)

fun CreateEventDraft.toDto(
    bannerUrl: String,
) = CreateEventDto(
    name = title,
    description = description,
    bannerUrl = bannerUrl,
    location = location,
    date = date,
    time = startTime,
    endTime= endTime,
    categoryId = categoryId,
    longitude = pinLongitude,
    latitude = pinLatitude,
)