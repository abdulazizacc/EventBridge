package com.uni.eventbridge.data.remote.dto

import com.uni.eventbridge.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("full_name")
    val fullName: String,
    val email: String?,
    @SerialName("avatar_url") val
    avatarUrl: String? = null,
)

fun ProfileDto.toDomain() = User(
    fullName = fullName,
    email = email ?: "",
    avatarUrl = avatarUrl.orEmpty()
)