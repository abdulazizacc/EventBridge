package com.uni.eventbridge.presentation.auth

import com.uni.eventbridge.domain.repository.AccountRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class AuthViewModel(
    private val accountRepository: AccountRepository,
) : BaseViewModel<AuthUiState, AuthEffect>(AuthUiState()) {

    init {
        observeSession()
    }

    private fun observeSession() {
        tryToCollect(
            flowProvider = { accountRepository.observeAuthState() },
            onNewValue = { isAuthenticated ->
                if (isAuthenticated) {
                    updateState { it.copy(isAuthenticated = true, isLoading = false) }
                } else {
                    updateState {
                        it.copy(
                            isAuthenticated = false,
                            isLoading = false
                        )
                    }
                }
            },
            onError = { e ->
                updateState { it.copy(isLoading = false, error = e.message) }
            }
        )
    }

    fun signInWithGoogle() {
        tryToExecute(
            callee = { accountRepository.signInWithGoogle() },
            onStart = { updateState { it.copy(isLoading = true, error = null) } },
            onSuccess = {},
            onError = { e ->
                updateState { it.copy(error = e.message ?: "Sign in failed") }
            },
            onFinally = {
                updateState { it.copy(isLoading = false) }
            }
        )
    }
}
