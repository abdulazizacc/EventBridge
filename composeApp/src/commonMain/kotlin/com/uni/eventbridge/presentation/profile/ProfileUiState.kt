package com.uni.eventbridge.presentation.profile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val fullName: String = "",
    val avatarUrl: String? = null,
    val attendedCount: Int = 0,
    val organizedCount: Int = 0,
    val showSignOutDialog: Boolean = false,
    val showTermOfServiceDialog: Boolean = false,
)