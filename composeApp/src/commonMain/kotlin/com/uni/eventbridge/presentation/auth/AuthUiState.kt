package com.uni.eventbridge.presentation.auth

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isAdmin: Boolean = false,
    val error: String? = null
)
