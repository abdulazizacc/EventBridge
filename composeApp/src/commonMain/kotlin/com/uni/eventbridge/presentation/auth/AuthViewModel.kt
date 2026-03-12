package com.uni.eventbridge.presentation.auth

import com.uni.eventbridge.domain.repository.AuthRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class AuthViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel<AuthUiState, AuthEffect>(AuthUiState()) {

    init {
        observeSession()
    }

    private fun observeSession() {
        tryToCollect(
            flowProvider = { authRepository.observeAuthState() },
            onNewValue = { isAuthenticated ->
                updateState {
                    it.copy(
                        isLoading = false,
                        isAuthenticated = isAuthenticated
                    )
                }
            },
            onError = { e ->
                updateState { it.copy(isLoading = false, error = e.message) }
            }
        )
    }
    fun signInWithGoogle() {
        tryToExecute(
            callee = { authRepository.signInWithGoogle() },
            onStart = { updateState { it.copy(isLoading = true, error = null) } },
            onSuccess = { },
            onError = { e ->
                updateState { it.copy(error = e.message ?: "Sign in failed") }
            },
            onFinally = { updateState { it.copy(isLoading = false) } }
        )
    }
}
