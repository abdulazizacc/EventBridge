package com.uni.eventbridge.presentation.createEvent.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uni.eventbridge.domain.model.Location
import com.uni.eventbridge.presentation.common.component.EventTextField
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.White
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_add
import org.jetbrains.compose.resources.painterResource
import kotlin.math.pow
import kotlin.math.roundToInt

@Composable
fun LocationSection(
    location: String,
    pinnedLocation: Location?,
    userLocation: Location?,
    onLocationChanged: (String) -> Unit,
    onMapPinned: (Double, Double) -> Unit,
) {
    var showMap by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Location")

        EventTextField(
            value = location,
            onValueChange = onLocationChanged,
            hint = "Room number or venue name",
            leadingIcon = painterResource(Res.drawable.ic_add),
            modifier = Modifier.fillMaxWidth(),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF0F2F8))
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                .clickable { showMap = true },
            contentAlignment = Alignment.Center,
        ) {
            if (pinnedLocation != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_add),
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "📍 ${pinnedLocation.latitude.toFixed(5)}, ${
                            pinnedLocation.longitude.toFixed(
                                5
                            )
                        }",
                        fontSize = 12.sp,
                        color = Color(0xFF0D1B2A)
                    )
                    Text("Tap to change", fontSize = 11.sp, color = Color(0xFF888888))
                }
            } else {
                Button(
                    onClick = { showMap = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text("Pin on Map", color = White)
                }
            }
        }
    }

    if (showMap) {
        key(userLocation) {
            PinLocationMapOverlay(
                userLocation = userLocation,
                onDismiss = { showMap = false },
                onLocationPinned = { lat, lon ->
                    onMapPinned(lat, lon)
                    showMap = false
                }
            )
        }
    }
}

fun Double.toFixed(decimals: Int): String {
    val factor = 10.0.pow(decimals)
    val rounded = (this * factor).roundToInt() / factor
    return rounded.toString()
}
