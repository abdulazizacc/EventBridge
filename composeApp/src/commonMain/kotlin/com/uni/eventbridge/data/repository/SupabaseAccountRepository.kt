package com.uni.eventbridge.data.repository

import com.uni.eventbridge.data.remote.GoogleSignInHelper
import com.uni.eventbridge.data.remote.dto.ProfileDto
import com.uni.eventbridge.data.remote.dto.toDomain
import com.uni.eventbridge.data.remote.supabase
import com.uni.eventbridge.domain.entity.User
import com.uni.eventbridge.domain.repository.AccountRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SupabaseAccountRepository(
    private val googleSignInHelper: GoogleSignInHelper
) : AccountRepository {

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

    override suspend fun getCurrentProfile(): User {
        val userId = supabase.auth.currentUserOrNull()?.id
            ?: error("No authenticated user")

        return supabase.postgrest["profiles"]
            .select { filter { eq("id", userId) } }
            .decodeSingle<ProfileDto>()
            .toDomain()
    }

    override suspend fun updateProfile(fullName: String, avatarUrl: String?): User {
        val userId = supabase.auth.currentUserOrNull()?.id
            ?: error("No authenticated user")

        return supabase.postgrest["profiles"]
            .update(
                buildMap {
                    put("full_name", fullName)
                    avatarUrl?.let { put("avatar_url", it) }
                }
            ) { filter { eq("id", userId) } }
            .decodeSingle<ProfileDto>()
            .toDomain()
    }

    override suspend fun isAdmin(): Boolean {
        return try {
            val response = supabase.postgrest.rpc("is_admin")
            val result = try {
                response.decodeSingle<Boolean>()
            } catch (e: Exception) {
                try {
                    response.decodeList<Boolean>().firstOrNull() ?: false
                } catch (e2: Exception) {
                    val body = response.data
                    body.contains("true", ignoreCase = true)
                }
            }
            result
        } catch (e: Exception) {
            false
        }
    }
}
