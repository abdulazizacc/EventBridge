package com.uni.eventbridge.presentation.search

import androidx.lifecycle.viewModelScope
import com.uni.eventbridge.domain.repository.SearchRepository
import com.uni.eventbridge.presentation.common.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchUiState, SearchEffect>(SearchUiState()) {

    private var searchJob: Job? = null

    init {
        observeSearchHistory()
    }

    private fun observeSearchHistory() {
        tryToCollect(
            flowProvider = { searchRepository.observeSearchHistory() },
            onNewValue = { history ->
                updateState { it.copy(searchHistory = history) }
            },
            onError = { e ->
                updateState { it.copy(error = e.message) }
            }
        )
    }

    fun onQueryChanged(query: String) {
        updateState { it.copy(query = query, error = null) }

        searchJob?.cancel()

        if (query.isBlank()) {
            updateState { it.copy(searchResults = emptyList(), isLoading = false) }
            return
        }

        searchJob = viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            delay(300) // debounce
            searchRepository.saveSearchQuery(query)
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        tryToExecute(
            callee = { searchRepository.searchEvents(query) },
            onSuccess = { results ->
                updateState { it.copy(searchResults = results, isLoading = false) }
            },
            onError = { e ->
                updateState { it.copy(error = e.message, isLoading = false) }
            }
        )
    }

    fun onSearchSubmitted() {
        val query = currentState.query
        if (query.isBlank()) return
        tryToExecute(
            callee = { searchRepository.saveSearchQuery(query) },
            onSuccess = {}
        )
        performSearch(query)
    }

    fun onHistoryItemClicked(query: String) {
        updateState { it.copy(query = query) }
        performSearch(query)
    }

    fun onDeleteHistoryItem(query: String) {
        tryToExecute(
            callee = { searchRepository.deleteSearchQuery(query) },
            onSuccess = {}
        )
    }

    fun onClearAllHistory() {
        tryToExecute(
            callee = { searchRepository.clearAllSearchHistory() },
            onSuccess = {}
        )
    }

    fun onEventClicked(eventId: Long) {
        sendEffect(SearchEffect.NavigateToEventDetail(eventId))
    }

}