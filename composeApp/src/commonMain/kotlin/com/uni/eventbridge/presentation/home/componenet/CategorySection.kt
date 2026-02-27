package com.uni.eventbridge.presentation.home.componenet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.designSystem.EventChip
import com.uni.eventbridge.presentation.home.HomeUiState.CategoryUiState


@Composable
fun CategorySection(
    allCategories: List<CategoryUiState>,
    selectedCategory: Long?,
    onCategorySelected: (CategoryUiState?) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .wrapContentSize(),
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        item {
            EventChip(
                title = "All",
                isSelected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
            )
        }
        items(allCategories.size) { index ->
            val category = allCategories[index]
            EventChip(
                title = category.name,
                isSelected = selectedCategory == category.id,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}
