package com.uni.eventbridge.data.remote.dto;

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MembershipInsert(
    @SerialName("event_id") val eventId: Long,
    @SerialName("user_id") val userId: String,
)
