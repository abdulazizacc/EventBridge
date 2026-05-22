package com.uni.eventbridge.presentation.createEvent.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uni.eventbridge.presentation.common.component.EventBridgeScaffold
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.White
import com.uni.eventbridge.presentation.common.component.topBar.DefaultTopBar
import com.uni.eventbridge.presentation.createEvent.CreateEventUiState
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_add
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndLocationStep(
    uiState: CreateEventUiState.DateAndLocationUiState,
    currentStepIndex: Int,
    totalSteps: Int,
    validationError: String? = null,
    onDateSelected: (String) -> Unit,
    onStartTimeChanged: (String) -> Unit,
    onEndTimeChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onMapPinned: (Double, Double) -> Unit,
    onNextStep: () -> Unit,
    onBackClicked: () -> Unit,
) {
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    val startTimePickerState = rememberTimePickerState(initialHour = 6, initialMinute = 0)
    val endTimePickerState = rememberTimePickerState(initialHour = 20, initialMinute = 30)

    EventBridgeScaffold(
        topBar = {
            DefaultTopBar(
                title = "Create Event",
                onBackClick = onBackClicked,
            )
        },
        bottomBar = {
            StepBottomBar(
                nextLabel = "Next Step",
                onNextClicked = onNextStep,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            StepProgressHeader(
                stepLabel = "STEP ${currentStepIndex + 1} OF $totalSteps",
                stepSublabel = "Date & Location",
                progressFraction = (currentStepIndex + 1) / totalSteps.toFloat(),
            )

            if (validationError != null) {
                Text(
                    text = validationError,
                    fontSize = 13.sp,
                    color = Color(0xFFB00020),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x1AB00020), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                )
            }

            EventDateSection(
                onDateSelected = onDateSelected,
            )

            TimeSection(
                startTime = uiState.startTime.orEmpty(),
                endTime = uiState.endTime.orEmpty(),
                onStartPickerToggle = { showStartTimePicker = true },
                onEndPickerToggle = { showEndTimePicker = true },
            )

            LocationSection(
                location = uiState.location.orEmpty(),
                pinnedLocation = uiState.pinnedLocation,
                userLocation = uiState.userLocation,
                onLocationChanged = onLocationChanged,
                onMapPinned = onMapPinned,
            )

        }
    }

    if (showStartTimePicker) {
        TimePickerDialog(
            onDismiss = { showStartTimePicker = false },
            onConfirm = {
                onStartTimeChanged(
                    formatTime(
                        startTimePickerState.hour,
                        startTimePickerState.minute
                    )
                )
                showStartTimePicker = false
            },
        ) {
            TimePicker(
                state = startTimePickerState,
                colors = timePickerColors()
            )
        }
    }

    if (showEndTimePicker) {
        TimePickerDialog(
            onDismiss = { showEndTimePicker = false },
            onConfirm = {
                onEndTimeChanged(formatTime(endTimePickerState.hour, endTimePickerState.minute))
                showEndTimePicker = false
            },
        ) {
            TimePicker(
                state = endTimePickerState,
                colors = timePickerColors()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDateSection(
    onDateSelected: (String) -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    datePickerState.selectedDateMillis?.let { millis ->
        val date = Instant.fromEpochMilliseconds(millis)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
        val month = date.month.name
            .take(3)
            .lowercase()
            .replaceFirstChar { it.uppercase() }
        LaunchedEffect(millis) {
            onDateSelected("$month ${date.dayOfMonth}, ${date.year}")
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Event Date")

        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            title = null,
            headline = null,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
            colors = DatePickerDefaults.colors(
                containerColor = White,
                selectedDayContainerColor = Primary,
                todayDateBorderColor = Primary,
                todayContentColor = Primary,
            )
        )
    }
}

@Composable
fun TimeSection(
    startTime: String,
    endTime: String,
    onStartPickerToggle: () -> Unit,
    onEndPickerToggle: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Time")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TimePickerField(
                label = "Start Time",
                value = startTime,
                modifier = Modifier.weight(1f),
                onClick = onStartPickerToggle,
            )
            TimePickerField(
                label = "End Time",
                value = endTime,
                modifier = Modifier.weight(1f),
                onClick = onEndPickerToggle,
            )
        }
    }
}

@Composable
fun TimePickerField(
    label: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(label, fontSize = 12.sp, color = Color(0xFF888888))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(10.dp))
                .clickable { onClick() }
                .padding(horizontal = 12.dp, vertical = 14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = value.ifEmpty { "--:-- --" },
                    fontSize = 14.sp,
                    color = if (value.isEmpty()) Color(0xFFAAAAAA) else Color(0xFF0D1B2A),
                )
                Icon(
                    painter = painterResource(Res.drawable.ic_add),
                    contentDescription = null,
                    tint = Color(0xFF888888),
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}


@Composable
private fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFF888888))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("OK", color = Primary)
            }
        },
        containerColor = White,
        textContentColor = Primary,
        text = { content() },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun timePickerColors() = TimePickerDefaults.colors(
    clockDialColor = Color(0xFFF4F6FA),
    clockDialSelectedContentColor = Color.White,
    clockDialUnselectedContentColor = Color(0xFF0D1B2A),
    selectorColor = Primary,
    containerColor = Color.White,
    periodSelectorBorderColor = Primary,
    periodSelectorSelectedContainerColor = Primary,
    periodSelectorUnselectedContainerColor = Color.White,
    periodSelectorSelectedContentColor = Color.White,
    periodSelectorUnselectedContentColor = Color(0xFF0D1B2A),
    timeSelectorSelectedContainerColor = Primary.copy(alpha = 0.1f),
    timeSelectorUnselectedContainerColor = Color(0xFFF4F6FA),
    timeSelectorSelectedContentColor = Primary,
    timeSelectorUnselectedContentColor = Color(0xFF0D1B2A),
)

private fun formatTime(hour: Int, minute: Int): String {
    val amPm = if (hour < 12) "AM" else "PM"
    val displayHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    val minuteStr = if (minute < 10) "0$minute" else "$minute"
    val hourStr = if (displayHour < 10) "0$displayHour" else "$displayHour"
    return "$hourStr:$minuteStr $amPm"
}