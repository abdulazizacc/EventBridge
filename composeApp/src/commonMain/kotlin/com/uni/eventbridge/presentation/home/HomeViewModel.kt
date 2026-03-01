package com.uni.eventbridge.presentation.home

import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.presentation.common.BaseViewModel

class HomeViewModel(
    private val eventRepository: EventRepository
) : BaseViewModel<HomeUiState, HomeUIEffect>(
    initialState = HomeUiState()
) {

    init {
        loadCategories()
        loadEvents()
    }

    private fun loadCategories() {
        tryToExecute(
            callee = {
                eventRepository.getCategory()
            },
            onSuccess = { categories ->
                updateState { it.copy(allCategories = categories.map{it.toUiState()}) }
            },
        )
    }

    fun loadEvents() {
        val categoryId = currentState.selectedCategoryId

        tryToExecute(
            callee = {
                eventRepository.getEventByCategory(categoryId)
            },
            onStart = {
                updateState {
                    it.copy(isLoading = true)
                }
            },
            onSuccess = { events ->
                updateState { it.copy(isLoading = false, events = events.map { it.toUiState() })  }
            },
        )
    }

    fun onCategorySelected(categoryId: Long?) {
        if (categoryId == currentState.selectedCategoryId) return
        updateState { it.copy(selectedCategoryId = categoryId) }
        loadEvents()
    }

    fun onEventClicked(eventId: Long) {
        sendEffect(HomeUIEffect.NavigateToEventDetails(eventId))
    }

}