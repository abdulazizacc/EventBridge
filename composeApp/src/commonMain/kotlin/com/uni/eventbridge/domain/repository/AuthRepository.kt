package com.uni.eventbridge.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signOut(): Unit
    suspend fun isAuthenticated(): Boolean
    suspend fun signInWithGoogle()
    fun observeAuthState(): Flow<Boolean>
}