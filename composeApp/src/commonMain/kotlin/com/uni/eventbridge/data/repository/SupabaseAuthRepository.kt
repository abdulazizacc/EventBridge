package com.uni.eventbridge.data.repository

import com.uni.eventbridge.data.remote.GoogleSignInHelper
import com.uni.eventbridge.data.remote.supabase
import com.uni.eventbridge.domain.repository.AuthRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SupabaseAuthRepository (
    private val googleSignInHelper: GoogleSignInHelper
): AuthRepository {

    override suspend fun signInWithGoogle() {
        val idToken = googleSignInHelper.getGoogleIdToken()

        supabase.auth.signInWith(IDToken) {
            this.idToken = idToken
            this.provider = Google
        }
    }

    override fun observeAuthState(): Flow<Boolean> =
        supabase.auth.sessionStatus.map { status ->
            status is SessionStatus.Authenticated
        }

    override suspend fun signOut() {
        supabase.auth.signOut()
    }

    override suspend fun isAuthenticated(): Boolean {
        return supabase.auth.currentUserOrNull() != null
    }
}