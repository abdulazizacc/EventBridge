package com.uni.eventbridge.data.remote

interface GoogleSignInHelper {
    suspend fun getGoogleIdToken(): String
}