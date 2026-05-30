package com.uni.eventbridge.presentation.home

import androidx.lifecycle.viewModelScope
import app.cash.paging.Pager
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import app.cash.paging.map
import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.presentation.common.BasePagingSource
import com.uni.eventbridge.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeViewModel(
    private val eventRepository: EventRepository
) : BaseViewModel<HomeUiState, HomeUIEffect>(
    initialState = HomeUiState()
) {

    init {
        loadCategories()
        fetchEventsByCategory(null)
    }

    private fun loadCategories() {
        tryToExecute(
            callee = {
                eventRepository.getCategory()
            },
            onSuccess = { categories ->
                updateState { it.copy(allCategories = categories.map { it.toUiState() }) }
            },
        )
    }


    fun onCategorySelected(categoryId: Long?) {
        if (categoryId == currentState.selectedCategoryId) return
        updateState { it.copy(selectedCategoryId = categoryId) }
        fetchEventsByCategory(categoryId)
    }

    private fun fetchEventsByCategory(categoryId: Long?) {
        updateState { it.copy(eventsFlow = buildEventsFlow(categoryId)) }
    }

    fun onEventClicked(eventId: Long) {
        sendEffect(HomeUIEffect.NavigateToEventDetails(eventId))
    }

    private fun buildEventsFlow(categoryId: Long?): Flow<PagingData<HomeUiState.EventUiState>> {
        return Pager(
            config = app.cash.paging.PagingConfig(pageSize = 20, prefetchDistance = 3),
            pagingSourceFactory = {
                BasePagingSource { page, pageSize ->
                    eventRepository.getEventByCategory(categoryId, page, pageSize).items
                }
            }
        ).flow
            .map { pagingData -> pagingData.map { it.toUiState() } }
            .cachedIn(viewModelScope)
    }
}
