package com.uni.eventbridge.presentation.search

import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.model.SearchHistoryItem

data class SearchUiState(
    val query: String = "",
    val searchHistory: List<SearchHistoryItem> = emptyList(),
    val searchResults: List<Event> = emptyList(),
    val isSearching: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val showHistory: Boolean get() = query.isBlank()
    val showResults: Boolean get() = query.isNotBlank() && !isLoading
    val showEmptyResults: Boolean get() = showResults && searchResults.isEmpty() && !isSearching
}