package com.uni.eventbridge.presentation.auth

import com.uni.eventbridge.domain.repository.AuthRepository
import com.uni.eventbridge.domain.repository.ProfileRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
) : BaseViewModel<AuthUiState, AuthEffect>(AuthUiState()) {

    init {
        observeSession()
    }

    private fun observeSession() {
        tryToCollect(
            flowProvider = { authRepository.observeAuthState() },
            onNewValue = { isAuthenticated ->
                if (isAuthenticated) {
                    updateState { it.copy(isAuthenticated = true, isLoading = false) }
                    checkAdminStatus()
                } else {
                    updateState {
                        it.copy(
                            isAuthenticated = false,
                            isAdmin = false,
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

    private fun checkAdminStatus() {
        tryToExecute(
            callee = { profileRepository.isAdmin() },
            onSuccess = { isAdmin ->
                updateState { it.copy(isAdmin = isAdmin) }
            },
            onError = {
                updateState { it.copy(isAdmin = false) }
            }
        )
    }

    fun signInWithGoogle() {
        tryToExecute(
            callee = { authRepository.signInWithGoogle() },
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
