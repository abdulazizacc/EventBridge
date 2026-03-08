package com.uni.eventbridge.data.remoteData.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Long,
    val name: String,
    val icon: String? =null
)