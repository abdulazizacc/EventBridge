package com.uni.eventbridge.presentation.home

data class HomeUiState(
    val allCategories: List<CategoryUiState> = emptyList(),
    val selectedCategoryId: Long? = null,
    val events: List<EventUiState> = emptyList(),
    val isLoading: Boolean = false,
    ) {
    data class EventUiState(
        val id: Long = 0,
        val title: String = "",
        val description: String = "",
        val bannerUrl: String = "",
        val location: String = "",
        val date: String = "",
        val isActive: Boolean = true,
        val isLoading: Boolean = false,
        val category: CategoryUiState = CategoryUiState()
    )

    data class CategoryUiState(
        val id: Long = 0L,
        val name: String = ""
    )
}