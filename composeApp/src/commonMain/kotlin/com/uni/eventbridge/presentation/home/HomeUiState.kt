package com.uni.eventbridge.presentation.home

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class HomeUiState(
    val allCategories: List<CategoryUiState> = emptyList(),
    val selectedCategoryId: Long? = null,
    val eventsFlow: Flow<PagingData<EventUiState>> = flowOf(PagingData.empty()),
    val isLoading: Boolean = false,
) {
    data class EventUiState(
        val id: Long = 0,
        val title: String = "",
        val description: String = "",
        val bannerUrl: String = "",
        val location: String = "",
        val date: String = "",
        val isLoading: Boolean = false,
        val category: CategoryUiState = CategoryUiState(),
        val isFull: Boolean = false,
        val isRegistered: Boolean = false,
    )

    data class CategoryUiState(
        val id: Long = 0L,
        val name: String = ""
    )
}
