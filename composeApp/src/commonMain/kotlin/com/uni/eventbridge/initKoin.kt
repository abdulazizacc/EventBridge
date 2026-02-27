package com.uni.eventbridge

import com.uni.eventbridge.di.homeModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(extraConfig: KoinAppDeclaration? = null) {
    startKoin {
        extraConfig?.invoke(this)
        modules(
            homeModule
        )
    }
}