package com.uni.eventbridge.presentation.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uni.eventbridge.domain.entity.Event
import com.uni.eventbridge.domain.model.SearchHistoryItem
import com.uni.eventbridge.presentation.common.component.EventCard
import com.uni.eventbridge.presentation.common.component.theme.Primary
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_close
import eventbridge.composeapp.generated.resources.ic_explore
import eventbridge.composeapp.generated.resources.ic_history
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    onNavigateToEventDetail: (Long) -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateToEventDetail -> onNavigateToEventDetail(effect.eventId)
            }
        }
    }

    SearchScreenContent(
        uiState = uiState,
        onQueryChanged = viewModel::onQueryChanged,
        onSearchSubmitted = viewModel::onSearchSubmitted,
        onHistoryItemClicked = viewModel::onHistoryItemClicked,
        onDeleteHistoryItem = viewModel::onDeleteHistoryItem,
        onClearAllHistory = viewModel::onClearAllHistory,
        onEventClicked = viewModel::onEventClicked,
    )
}

@Composable
private fun SearchScreenContent(
    uiState: SearchUiState,
    onQueryChanged: (String) -> Unit,
    onSearchSubmitted: () -> Unit,
    onHistoryItemClicked: (String) -> Unit,
    onDeleteHistoryItem: (String) -> Unit,
    onClearAllHistory: () -> Unit,
    onEventClicked: (Long) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchTopBar(
            query = uiState.query,
            onQueryChanged = onQueryChanged,
            onClearQuery = { onQueryChanged("") },
            onSearchSubmitted = {
                keyboardController?.hide()
                onSearchSubmitted()
            },
            focusRequester = focusRequester,
        )

        HorizontalDivider(color = Color(0xFFEEF0F4), thickness = 1.dp)

        AnimatedContent(
            targetState = uiState.showHistory,
            transitionSpec = {
                (fadeIn(tween(200)) + slideInVertically { it / 10 })
                    .togetherWith(fadeOut(tween(150)))
            },
            label = "search_content"
        ) { showHistory ->
            if (showHistory) {
                SearchHistorySection(
                    history = uiState.searchHistory,
                    onItemClicked = {
                        keyboardController?.hide()
                        onHistoryItemClicked(it)
                    },
                    onDeleteItem = onDeleteHistoryItem,
                    onClearAll = onClearAllHistory,
                )
            } else {
                SearchResultsSection(
                    results = uiState.searchResults,
                    isLoading = uiState.isLoading,
                    isEmpty = uiState.showEmptyResults,
                    query = uiState.query,
                    onEventClicked = onEventClicked,
                )
            }
        }
    }
}

@Composable
private fun SearchTopBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    onSearchSubmitted: () -> Unit,
    focusRequester: FocusRequester,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color(0xFFF4F6FA),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_explore),
                contentDescription = null,
                tint = Color(0xFF8A94A6),
                modifier = Modifier.size(20.dp)
            )

            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = "Search hackathons, workshops, clubs...",
                        color = Color(0xFF8A94A6),
                        fontSize = 14.sp,
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF0D1B2A),
                        fontWeight = FontWeight.Normal
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(Primary),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearchSubmitted() })
                )
            }

            AnimatedVisibility(visible = query.isNotEmpty()) {
                Icon(
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = "Clear",
                    tint = Color(0xFF8A94A6),
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { onClearQuery() }
                )
            }
        }
    }
}

@Composable
private fun SearchHistorySection(
    history: List<SearchHistoryItem>,
    onItemClicked: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
    onClearAll: () -> Unit,
) {
    if (history.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No recent searches",
                color = Color(0xFFB0B8C6),
                fontSize = 14.sp
            )
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "RECENT SEARCHES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8A94A6),
                letterSpacing = 1.sp
            )
            TextButton(onClick = onClearAll) {
                Text(
                    text = "Clear All",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Primary
                )
            }
        }

        LazyColumn {
            items(items = history) { item ->
                SearchHistoryItem(
                    query = item.query,
                    onClicked = { onItemClicked(item.query) },
                    onDelete = { onDeleteItem(item.query) }
                )
            }
        }
    }
}

@Composable
private fun SearchHistoryItem(
    query: String,
    onClicked: () -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_history),
            contentDescription = null,
            tint = Color(0xFFB0B8C6),
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = query,
            fontSize = 15.sp,
            color = Color(0xFF0D1B2A),
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_close),
                contentDescription = "Remove",
                tint = Color(0xFFB0B8C6),
                modifier = Modifier.size(16.dp)
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 20.dp),
        color = Color(0xFFF4F6FA),
        thickness = 1.dp
    )
}

@Composable
private fun SearchResultsSection(
    results: List<Event>,
    isLoading: Boolean,
    isEmpty: Boolean,
    query: String,
    onEventClicked: (Long) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Primary,
                    strokeWidth = 2.dp
                )
            }

            isEmpty -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "No results for",
                        fontSize = 15.sp,
                        color = Color(0xFF8A94A6)
                    )
                    Text(
                        text = "\"$query\"",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0D1B2A)
                    )
                }
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    item {
                        Text(
                            text = "${results.size} result${if (results.size != 1) "s" else ""} found",
                            fontSize = 12.sp,
                            color = Color(0xFF8A94A6),
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )
                    }
                    items(items = results, key = { it.id }) { event ->
                        EventCard(
                            title = event.name,
                            date = event.date,
                            location = event.venueName,
                            imageUrl = event.bannerUrl,
                            state = event.category.name,
                            onClick = { onEventClicked(event.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    event: Event,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = Primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = event.category.name.take(1),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Primary
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0D1B2A),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${event.date} · ${event.venueName}",
                fontSize = 12.sp,
                color = Color(0xFF8A94A6),
                maxLines = 1
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 20.dp),
        color = Color(0xFFF4F6FA),
        thickness = 1.dp
    )
}