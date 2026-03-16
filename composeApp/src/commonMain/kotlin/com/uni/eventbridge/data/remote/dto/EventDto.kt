package com.uni.eventbridge.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("banner_url")
    val bannerUrl: String,
    @SerialName("location")
    val location: String? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("category")
    val category: CategoryDto? = null,
    @SerialName("time")
    val startTime: String? = null,
    @SerialName("end_time")
    val endTime: String? = null,
    @SerialName("venue_name")
    val venueName: String? = null,
    @SerialName("venue_detail")
    val venueDetail: String? = null,
    @SerialName("organizer_name")
    val organizer: String? = null,
    @SerialName("max_attendees")
    val maxAttendees: Int? = null,
    @SerialName("remaining_seats")
    val remainingSeats: Int? = null,
    @SerialName("pin_latitude")
    val latitude: Double? = null,
    @SerialName("pin_longitude")
    val longitude: Double? = null
)
