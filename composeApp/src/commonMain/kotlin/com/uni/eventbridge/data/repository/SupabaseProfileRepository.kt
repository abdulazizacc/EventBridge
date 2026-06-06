package com.uni.eventbridge.data.repository

import com.uni.eventbridge.data.remote.dto.ProfileDto
import com.uni.eventbridge.data.remote.dto.toDomain
import com.uni.eventbridge.data.remote.supabase
import com.uni.eventbridge.domain.entity.User
import com.uni.eventbridge.domain.repository.ProfileRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest

class SupabaseProfileRepository : ProfileRepository {

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

    override suspend fun signOut() {
        supabase.auth.signOut()
    }

    override suspend fun isAdmin(): Boolean {
        return try {
            val response = supabase.postgrest.rpc("is_admin")
            val result = try {
                response.decodeSingle<Boolean>()
            } catch (e: Exception) {
                response.decodeList<Boolean>().firstOrNull() ?: false
            }
            result
        } catch (e: Exception) {
            throw e
        }
    }
}
