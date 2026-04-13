package com.uni.eventbridge.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uni.eventbridge.presentation.common.component.theme.White
import com.uni.eventbridge.presentation.common.component.topBar.BadgeTopBar
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeUIEffect.NavigateToEventDetails -> onNavigateToEventDetail(effect.eventId)
                HomeUIEffect.ShowJoinSuccessSnackbar ->
                    snackbarHostState.showSnackbar("You've joined the event!")

                HomeUIEffect.ShowLeaveSuccessSnackbar ->
                    snackbarHostState.showSnackbar("You've left the event")

                is HomeUIEffect.ShowErrorSnackbar ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            uiState = uiState,
            onCategorySelected = viewModel::onCategorySelected,
            onEventClicked = viewModel::onEventClicked,
            onJoinClick = viewModel::onJoinClick,
            onLeaveClick = viewModel::onLeaveClick,
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onCategorySelected: (Long?) -> Unit = {},
    onEventClicked: (Long) -> Unit = {},
    onJoinClick: (Long) -> Unit = {},
    onLeaveClick: (Long) -> Unit = {},
) {
    val categoryScrollState = rememberLazyListState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item(span = { GridItemSpan(maxLineSpan) }) {
            BadgeTopBar(
                title = "Discover Events",
                leadingIcon = painterResource(Res.drawable.ic_hat),
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            CategorySection(
                allCategories = uiState.allCategories,
                selectedCategory = uiState.selectedCategoryId,
                onCategorySelected = { onCategorySelected(it?.id) },
                listState = categoryScrollState,
                modifier = Modifier.padding(top = 14.dp),
            )
        }

        items(
            items = uiState.events,
            key = { it.id }
        ) { event ->
            HomeEventCard(
                imageUrl = event.bannerUrl.takeIf { it.isNotBlank() },
                date = event.date,
                title = event.title,
                location = event.location,
                category = event.category.name.takeIf { it.isNotBlank() },
                isJoined = event.isRegistered,
                isFull = event.isFull,
                isJoinInProgress = event.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onClick = { onEventClicked(event.id) },
                onJoinClick = { onJoinClick(event.id) },
                onLeaveClick = { onLeaveClick(event.id) },
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

