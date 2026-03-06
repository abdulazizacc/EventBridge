package com.uni.eventbridge.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uni.eventbridge.designSystem.theme.White
import com.uni.eventbridge.designSystem.topBar.BadgeTopBar
import com.uni.eventbridge.presentation.home.componenet.CategorySection
import com.uni.eventbridge.presentation.home.componenet.HomeEventCard
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_hat
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToEventDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeUIEffect.NavigateToEventDetails -> onNavigateToEventDetail(effect.eventId)
            }
        }
    }

    HomeContent(
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onEventClicked = viewModel::onEventClicked,
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onCategorySelected: (Long?) -> Unit = {},
    onEventClicked: (Long) -> Unit = {},
) {
    val categoryScrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            BadgeTopBar(
                title = "Discover Events",
                leadingIcon = painterResource(Res.drawable.ic_hat),
            )
        }

        item {
            CategorySection(
                allCategories = uiState.allCategories,
                selectedCategory = uiState.selectedCategoryId,
                onCategorySelected = { onCategorySelected(it?.id) },
                listState = categoryScrollState,
                modifier = Modifier.padding(top = 16.dp),
            )
        }

        items(
            items = uiState.events,
            key = { it.id },
        ) { event ->
            HomeEventCard(
                date = event.date,
                title = event.title,
                location = event.location,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { onEventClicked(event.id) },
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    HomeContent(
        uiState = HomeUiState(
            allCategories = listOf(
                HomeUiState.CategoryUiState(id = 1, name = "Music"),
                HomeUiState.CategoryUiState(id = 2, name = "Sports"),
                HomeUiState.CategoryUiState(id = 3, name = "Academic"),
            ),
            events = listOf(
                HomeUiState.EventUiState(
                    id = 1,
                    title = "Annual Spring Hackathon",
                    date = "FRI, OCT 25 • 6:00 PM",
                    location = "Student Union Hall, Main Campus",
                ),
                HomeUiState.EventUiState(
                    id = 2,
                    title = "Jazz Night Under the Stars",
                    date = "SAT, OCT 26 • 8:00 PM",
                    location = "Quad Courtyard Garden",
                ),
            )
        )
    )
}


