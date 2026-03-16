package com.uni.eventbridge.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.uni.eventbridge.PlatformLocationProvider
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

    single {
        LocationServices.getFusedLocationProviderClient(get<Context>())
    }
    single {
        PlatformLocationProvider(get())
    }
}