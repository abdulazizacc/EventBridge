package com.uni.eventbridge

import android.app.Application
import org.koin.android.ext.koin.androidContext

class EventBridgeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(){
            androidContext(this@EventBridgeApp)
        }
    }
}