package com.example.eventbridge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform