package com.uni.eventbridge.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE , EFFECT>(
    initialState: STATE
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<EFFECT>()
    val uiEffect = _uiEffect.asSharedFlow()

    protected val currentState: STATE
        get() = _uiState.value

    protected fun updateState(updater: (STATE) -> STATE) {
        _uiState.value = updater(_uiState.value)
    }

    protected fun sendEffect(effect: EFFECT) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
    protected fun <T> tryToExecute(
        callee: suspend () -> T,
        onSuccess: suspend (T) -> Unit,
        onError: suspend (Throwable) -> Unit ={},
        onStart: suspend () -> Unit = {},
        onFinally: suspend () -> Unit = {},
    ) = viewModelScope.launch {
        onStart()
        try {
            onSuccess(callee())
        } catch (e: Exception) {
            onError(e)
        } finally {
            onFinally()
        }
    }

    protected fun <T> tryToCollect(
        flowProvider: suspend () -> Flow<T>,
        onNewValue: suspend (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Job {
        val handler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }
        return viewModelScope.launch(handler) {
            flowProvider().distinctUntilChanged().collectLatest {
                onNewValue(it)
            }
        }
    }


}
