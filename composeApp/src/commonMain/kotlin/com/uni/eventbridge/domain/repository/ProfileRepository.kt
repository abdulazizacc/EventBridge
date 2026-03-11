package com.uni.eventbridge.domain.repository

import com.uni.eventbridge.domain.entity.User

interface ProfileRepository {
    suspend fun getCurrentProfile(): User
    suspend fun updateProfile(fullName: String, avatarUrl: String?): User
    suspend fun uploadAvatar(imageBytes: ByteArray): String
    suspend fun signOut()
}