package com.uni.eventbridge.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.uni.eventbridge.data.remote.GoogleSignInHelper
import kotlin.coroutines.cancellation.CancellationException

class AndroidGoogleSignInHelper(
    private val context: Context,
    private val webClientId: String
) : GoogleSignInHelper {

    override suspend fun getGoogleIdToken(): String {
        val credentialManager = CredentialManager.create(context)

        val signInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(webClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = GoogleIdTokenCredential.createFrom(result.credential.data)
            credential.idToken

        } catch (e: GetCredentialCancellationException) {
            throw CancellationException("User cancelled Google Sign-In")

        } catch (e: NoCredentialException) {
            throw Exception("No Google account found. Please add a Google account in device Settings.")

        } catch (e: GetCredentialException) {
            throw Exception("Sign-in failed: ${e.message}")
        }
    }
}