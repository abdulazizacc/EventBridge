package com.uni.eventbridge.presentation.profile

sealed interface ProfileUIEffect {
    data object NavigateToSignIn : ProfileUIEffect
    data object NavigateToContactUs : ProfileUIEffect
    data object NavigateToTermsOfService : ProfileUIEffect
}