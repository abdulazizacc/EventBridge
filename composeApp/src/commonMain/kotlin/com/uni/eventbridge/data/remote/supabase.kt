package com.uni.eventbridge.data.remote

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

val supabase = createSupabaseClient(
    supabaseUrl = "",
    supabaseKey = ""
) {
    install(Postgrest)
    install(Auth) {
        host = "login-callback"
        scheme = "com.uni.eventbridge"
    }
    install(Storage)
    install(ComposeAuth)
}