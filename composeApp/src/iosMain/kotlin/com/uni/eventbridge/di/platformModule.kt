package com.uni.eventbridge.di

import com.uni.eventbridge.PlatformLocationProvider
import org.koin.dsl.module

val platformModule = module {
    single { PlatformLocationProvider() }
}