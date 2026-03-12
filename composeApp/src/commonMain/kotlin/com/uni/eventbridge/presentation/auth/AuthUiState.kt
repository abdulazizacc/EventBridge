package com.uni.eventbridge.presentation.auth

data class AuthUiState(
    val isLoading: Boolean = true,
    val isAuthenticated: Boolean = false,
    val error: String? = null
)