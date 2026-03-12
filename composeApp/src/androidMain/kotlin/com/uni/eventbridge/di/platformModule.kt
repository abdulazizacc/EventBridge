package com.uni.eventbridge.di

import com.uni.eventbridge.auth.AndroidGoogleSignInHelper
import com.uni.eventbridge.data.remote.GoogleSignInHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single<GoogleSignInHelper> {
        AndroidGoogleSignInHelper(
            context = androidContext(),
            webClientId = ""
        )
    }
}