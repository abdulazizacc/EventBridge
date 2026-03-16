package com.uni.eventbridge

import android.app.Application
import com.uni.eventbridge.di.platformModule
import org.koin.android.ext.koin.androidContext

class EventBridgeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(){
            androidContext(this@EventBridgeApp)
            modules(platformModule,)
        }
    }
}