package com.uni.eventbridge.presentation.profile

import com.uni.eventbridge.domain.repository.AccountRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class ProfileViewModel(
    private val accountRepository: AccountRepository,
) : BaseViewModel<ProfileUiState, ProfileUIEffect>(
    initialState = ProfileUiState()
) {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        tryToExecute(
            callee = { accountRepository.getCurrentProfile() },
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
            callee = { accountRepository.signOut() },
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
