package com.uni.eventbridge.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateEventDto(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("banner_url")
    val bannerUrl: String,
    @SerialName("location")
    val location: String,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("category_id")
    val categoryId: Long,
    @SerialName("venue_name")
    val venueName: String? = null,
    @SerialName("venue_detail")
    val venueDetail: String? = null,
    @SerialName("max_attendees")
    val maxAttendees: Int? = null,
    @SerialName("pin_longitude")
    val longitude: Double? = null,
    @SerialName("pin_latitude")
    val latitude: Double? = null,
)