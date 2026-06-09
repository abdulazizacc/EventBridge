package com.uni.eventbridge.presentation.profile

import com.uni.eventbridge.domain.repository.ProfileRepository
import com.uni.eventbridge.presentation.auth.AuthViewModel
import com.uni.eventbridge.presentation.common.BaseViewModel
import io.github.aakira.napier.Napier

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
) : BaseViewModel<ProfileUiState, ProfileUIEffect>(
    initialState = ProfileUiState()
) {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        tryToExecute(
            callee = { profileRepository.getCurrentProfile() },
            onStart = { updateState { it.copy(isLoading = true) } },
            onSuccess = { user ->
                updateState {
                    it.copy(
                        fullName = user.fullName,
                        avatarUrl = user.avatarUrl,
                    )
                }
            },
            onFinally = {
                updateState { it.copy(isLoading = false) }
            }
        )
    }

    fun onContactUsClicked() {
        sendEffect(ProfileUIEffect.NavigateToContactUs)
    }

    fun onSignOutClicked() {
        updateState { it.copy(showSignOutDialog = true) }
    }

    fun onTermsOfServiceClicked() {
        updateState { it.copy(showTermOfServiceDialog = true) }
    }


    fun onSignOutConfirmed() {
        tryToExecute(
            callee = { profileRepository.signOut() },
            onStart = { updateState { it.copy(isLoading = true, showSignOutDialog = false) } },
            onSuccess = {
                updateState { it.copy(isLoading = false) }
                sendEffect(ProfileUIEffect.NavigateToSignIn)
            },
        )
    }

    fun onTermOfServiceDismissed() {
        updateState { it.copy(showTermOfServiceDialog = false) }
    }
    fun onSignOutDismissed() {
        updateState { it.copy(showSignOutDialog = false) }
    }
}