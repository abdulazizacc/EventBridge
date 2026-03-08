package com.uni.eventbridge.domain.model

data class CreateEventDraft(
    val title: String,
    val category: String,
    val department: String,
    val bannerBytes: ByteArray? = null,
    val date: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val pinLatitude: Double?,
    val pinLongitude: Double?,
    val description: String,
)