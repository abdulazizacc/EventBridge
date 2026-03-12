package com.uni.eventbridge.domain.entity

data class Event(
    val id: Long,
    val name: String,
    val description: String,
    val bannerUrl: String,
    val location: String,
    val date: String,
    val isActive: Boolean,
    val startTime: String,
    val endTime: String,
    val category: Category,
    val venueName: String,
    val venueDetail: String,
    val organizer: String,
    val maxAttendees: Int?,
    val remainingSeats: Int?,
)
