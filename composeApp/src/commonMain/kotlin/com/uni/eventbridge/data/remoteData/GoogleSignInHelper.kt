package com.uni.eventbridge.data.remoteData

interface GoogleSignInHelper {
    suspend fun getGoogleIdToken(): String
}