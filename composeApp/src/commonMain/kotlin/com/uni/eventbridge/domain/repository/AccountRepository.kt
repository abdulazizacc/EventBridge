package com.uni.eventbridge.domain.repository

import com.uni.eventbridge.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun signOut()
    suspend fun isAuthenticated(): Boolean
    suspend fun signInWithGoogle()
    fun observeAuthState(): Flow<Boolean>
    suspend fun getCurrentProfile(): User
    suspend fun updateProfile(fullName: String, avatarUrl: String?): User
    suspend fun isAdmin(): Boolean
}
