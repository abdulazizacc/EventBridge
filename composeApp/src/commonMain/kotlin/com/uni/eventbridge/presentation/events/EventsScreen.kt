package com.uni.eventbridge.presentation.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uni.eventbridge.designSystem.EventBridgeScaffold
import com.uni.eventbridge.designSystem.EventCard
import com.uni.eventbridge.designSystem.theme.Primary
import com.uni.eventbridge.designSystem.theme.White
import com.uni.eventbridge.designSystem.topBar.DefaultTopBar
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_add
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EventsScreen(
    viewModel: EventsViewModel = koinViewModel(),
    onNavigateToEventDetail: (Long) -> Unit,
    onCreateEventClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(  Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is EventsUIEffect.NavigateToEventDetails -> onNavigateToEventDetail(effect.eventId)
                is EventsUIEffect.NavigateToCreateEvent -> onCreateEventClicked()
            }
        }
    }

    EventsContent(
        uiState = uiState,
        onEventClicked = viewModel::onEventClicked,
        onCreateEventClicked = viewModel::onCreateEventClicked,
    )
}

@Composable
private fun EventsContent(
    uiState: EventsUiState = EventsUiState(),
    onEventClicked: (Long) -> Unit = {},
    onCreateEventClicked: () -> Unit = {},
) {
    EventBridgeScaffold(
        topBar = { DefaultTopBar(title = "My Events") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreateEventClicked() },
                containerColor = Primary,
                contentColor = White,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_add),
                    contentDescription = "Create Event",
                )
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = uiState.events,
                key = { it.id },
            ) { event ->
                EventCard(
                    title = event.title,
                    date = event.date,
                    location = event.location,
                    onClick = { onEventClicked(event.id) },
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EventsContentPreview() {
    EventsContent(
        uiState = EventsUiState(
            events = listOf(
                EventsUiState.EventUiState(
                    id = 1,
                    title = "Annual Science Fair",
                    date = "Oct 24, 2023 • 10:00 AM",
                    location = "Grand University Hall",
                    imageUrl = "",

                    ),
                EventsUiState.EventUiState(
                    id = 2,
                    title = "AI & Ethics Workshop",
                    date = "Oct 26, 2023 • 2:30 PM",
                    location = "Innovation Tech Hub",
                    imageUrl = "",
                ),
                EventsUiState.EventUiState(
                    id = 3,
                    title = "Winter Music Fest",
                    date = "Dec 12, 2023 • 6:00 PM",
                    location = "University Stadium",
                    imageUrl = "",
                ),
            )
        )
    )
}
