package com.uni.eventbridge.presentation.createEvent.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.uni.eventbridge.designSystem.EventBridgeScaffold
import com.uni.eventbridge.designSystem.theme.Primary
import com.uni.eventbridge.designSystem.topBar.DefaultTopBar
import com.uni.eventbridge.presentation.createEvent.CreateEventUiState
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_calendder
import eventbridge.composeapp.generated.resources.ic_location
import org.jetbrains.compose.resources.painterResource

@Composable
fun ReviewStep(
    uiState: CreateEventUiState,
    onBackClicked: () -> Unit,
    onPublishClicked: () -> Unit,
    onEditBasicInfo: () -> Unit,
    onEditDateAndLocation: () -> Unit,
) {
    EventBridgeScaffold(
        topBar = {
            DefaultTopBar(
                title = "Review Event",
                onBackClick = onBackClicked,
            )
        },
        bottomBar = {
            StepBottomBar(
                nextLabel = "Publish Event",
                onNextClicked = onPublishClicked,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StepProgressHeader(
                stepLabel = "STEP 3 OF 3",
                stepSublabel = "Final Review",
                progressFraction = 1f,
            )

            uiState.basicInfo.bannerBytes?.let {
                if (it.isNotEmpty()) {
                    AsyncImage(
                        model = it,
                        contentDescription = "Event Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    )
                }
            }

            // Basic Info Section
            ReviewSection(
                title = "Basic Info",
                onEditClick = onEditBasicInfo,
            ) {
                ReviewRow(label = "TITLE", value = uiState.basicInfo.title)
                ReviewRow(label = "CATEGORY", value = uiState.basicInfo.category)
                ReviewRow(label = "DEPARTMENT", value = uiState.basicInfo.department)
            }

            HorizontalDivider(color = Color(0xFFEEEEEE))

            // Date & Location Section
            ReviewSection(
                title = "Date & Location",
                onEditClick = onEditDateAndLocation,
            ) {
                ReviewIconRow(
                    icon = Res.drawable.ic_calendder,
                    value = "${uiState.dateAndLocation.date}  ${uiState.dateAndLocation.startTime} - ${uiState.dateAndLocation.endTime}",
                )
                ReviewIconRow(
                    icon = Res.drawable.ic_location,
                    value = uiState.dateAndLocation.location,
                )
            }

            HorizontalDivider(color = Color(0xFFEEEEEE))

            Spacer(Modifier.height(8.dp))

            Text(
                text = "STEP 3 OF 3: FINAL REVIEW",
                fontSize = 11.sp,
                color = Color(0xFFAAAAAA),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

// --- Review Section ---

@Composable
private fun ReviewSection(
    title: String,
    onEditClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D1B2A),
            )
            TextButton(onClick = onEditClick) {
                Text(
                    text = "Edit",
                    color = Primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        content()
    }
}

// --- Review Row (label + value) ---

@Composable
private fun ReviewRow(
    label: String,
    value: String,
) {
    if (value.isEmpty()) return
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFAAAAAA),
            letterSpacing = 0.5.sp,
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFF0D1B2A),
        )
    }
}

// --- Review Icon Row (icon + value) ---

@Composable
private fun ReviewIconRow(
    icon: org.jetbrains.compose.resources.DrawableResource,
    value: String,
) {
    if (value.isEmpty()) return
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color(0xFF888888),
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFF0D1B2A),
        )
    }
}