package com.uni.eventbridge.data.remoteData.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: Long,
    val name: String,
    val description: String,
    @SerialName("banner_url") val bannerUrl: String,
    val location: String,
    val date: String,
    @SerialName("is_active") val isActive: Boolean,
    val category: CategoryDto
)