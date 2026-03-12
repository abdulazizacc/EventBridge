package com.uni.eventbridge.presentation.auth

sealed class AuthEffect {
    data object NavigateToHome : AuthEffect()
    data class ShowError(val message: String) : AuthEffect()
}