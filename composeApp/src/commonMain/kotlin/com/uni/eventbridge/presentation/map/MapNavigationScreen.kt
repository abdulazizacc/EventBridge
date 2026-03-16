package com.uni.eventbridge.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uni.eventbridge.designSystem.theme.Primary
import com.uni.eventbridge.designSystem.theme.White
import com.uni.eventbridge.domain.model.Location
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MapNavigationScreen(
    eventLat: Double,
    eventLon: Double,
    onNavigateBack: () -> Unit,
    viewModel: NavigationViewModel = koinViewModel(
        parameters = { parametersOf(eventLat, eventLon) }
    ),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {

        NavigationMapView(
            userLocation = uiState.userLocation,
            eventLocation = Location(eventLat, eventLon),
            route = uiState.route?.polyline ?: emptyList(),
            isLoading = uiState.isLoading,
        )

        Box(
            Modifier
                .fillMaxWidth()
                .background(White.copy(alpha = 0.95f))
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Text("←", fontSize = 22.sp, color = Color(0xFF0D1B2A))
            }
            Text(
                "Navigate to Event",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D1B2A)
            )
        }

        uiState.route?.let { route ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    RouteInfoChip(
                        label = "Distance",
                        value = "${(route.distanceMeters / 1000.0 * 10).toLong() / 10.0} km"
                    )
                    RouteInfoChip(
                        label = "Duration",
                        value = formatDuration(route.durationSeconds)
                    )
                }
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Primary
            )
        }
    }
}

@Composable
private fun RouteInfoChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 12.sp, color = Color(0xFF888888))
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D1B2A))
    }
}

private fun formatDuration(seconds: Double): String {
    val mins = (seconds / 60).toInt()
    return if (mins < 60) "$mins min" else "${mins / 60}h ${mins % 60}m"
}