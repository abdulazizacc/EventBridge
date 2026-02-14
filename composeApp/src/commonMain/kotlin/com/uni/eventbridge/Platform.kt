package com.uni.eventbridge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform