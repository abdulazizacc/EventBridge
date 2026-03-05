package com.uni.eventbridge.presentation.eventDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uni.eventbridge.designSystem.theme.White
import com.uni.eventbridge.presentation.eventDetails.component.AboutSection
import com.uni.eventbridge.presentation.eventDetails.component.EventDetailsScaffold
import com.uni.eventbridge.presentation.eventDetails.component.EventHeader
import com.uni.eventbridge.presentation.eventDetails.component.EventInfoRows
import com.uni.eventbridge.presentation.eventDetails.component.HeroSection
import com.uni.eventbridge.presentation.eventDetails.component.JoinBottomBar
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun EventDetailsScreen(
    eventId: Long,
    viewModel: EventDetailsViewModel = koinViewModel(parameters = { parametersOf(eventId) }),
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is EventDetailsUIEffect.NavigateBack -> onNavigateBack()
                EventDetailsUIEffect.ShowJoinSuccessSnakeBar -> {}

            }
        }
    }

    EventDetailsContent(
        uiState = uiState,
        onBackClick = viewModel::onBackClicked,
    )
}

@Composable
fun EventDetailsContent(
    uiState: EventDetailsUiState = EventDetailsUiState(),
    onBackClick: () -> Unit = {},
    onJoinClick: () -> Unit = {},
) {
    EventDetailsScaffold(
        isLoading = uiState.isLoading,
        bottomBar = {
            JoinBottomBar(
                isJoined = uiState.isRegistered,
                onJoinClick = onJoinClick,
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
                .background(color = White),
        ) {
            item {
                Box {
                    HeroSection(
                        imageUrl = uiState.heroImageUrl,
                        onBackClick = onBackClick,
                    )

                    Column(
                        modifier = Modifier
                            .padding(top = 200.dp)
                            .fillMaxWidth()
                            .background(
                                color = White,
                                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    )
                    {
                        EventHeader(
                            category = uiState.category,
                            title = uiState.title,
                            organizer = uiState.organizer,
                            organizerAvatarUrl = uiState.organizerAvatarUrl,
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        EventInfoRows(
                            date = uiState.date,
                            timeRange = uiState.timeRange,
                            venueName = uiState.venueName,
                            venueDetail = uiState.venueDetail,
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        AboutSection(description = uiState.description)

                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EventDetailsScreenPreview() {
    MaterialTheme {
        EventDetailsContent(
            uiState = EventDetailsUiState(
                category = EventDetailsUiState.CategoryUiState(
                    id = 1L,
                    name = "Career Development"
                ),
                title = "Annual Spring Career Fair 2024",
                organizer = "Office of Student Affairs",
                date = "October 12, 2024",
                timeRange = "10:00 AM – 4:00 PM",
                venueName = "Great Hall, Student Union",
                venueDetail = "Main Campus, Level 2",
                description = "Join us for the largest networking event of the academic year. The Annual Spring Career Fair 2024 connects students and alumni with over 150 top-tier employers from technology, finance, healthcare, and creative industries.\n\nWhether you are looking for your first internship or a full-time graduate role, this fair provides a unique opportunity to meet recruiters face-to-face, attend live resume workshops, \nand participate in mock interviews. join as fast as you can or you will miss everything",
                totalAttendees = 45,
                isRegistered = false,
            )
        )
    }
}