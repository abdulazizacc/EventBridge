package com.uni.eventbridge.presentation.createEvent.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.uni.eventbridge.domain.model.Location
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.White
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.style.rememberStyleState
import org.maplibre.compose.util.ClickResult
import org.maplibre.spatialk.geojson.Position

@Composable
fun PinLocationMapOverlay(
    userLocation: Location?,
    onDismiss: () -> Unit,
    onLocationPinned: (Double, Double) -> Unit,
) {
    var pendingPin by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    val initialTarget = userLocation ?: Location(34.681265, 43.654822)
    val cameraState = rememberCameraState(
        firstPosition = CameraPosition(
            target = Position(
                longitude = initialTarget.longitude,
                latitude = initialTarget.latitude
            ),
            zoom = 17.5
        )
    )
    val styleState = rememberStyleState()

    Popup(
        properties = PopupProperties(focusable = true, dismissOnBackPress = true),
        onDismissRequest = onDismiss,
    ) {
        Box(Modifier.fillMaxSize()) {
            MaplibreMap(
                modifier = Modifier.fillMaxSize(),
                cameraState = cameraState,
                styleState = styleState,
                baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
                options = MapOptions(),
                onMapClick = { position, _ ->
                    pendingPin = Pair(position.latitude, position.longitude)
                    ClickResult.Pass
                }
            )

            Box(
                Modifier
                    .fillMaxWidth()
                    .background(White.copy(alpha = 0.95f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = Color(0xFF888888))
                    }
                    Text(
                        "Tap map to pin location",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF0D1B2A)
                    )
                    TextButton(
                        onClick = {
                            pendingPin?.let { onLocationPinned(it.first, it.second) }
                        },
                        enabled = pendingPin != null
                    ) {
                        Text(
                            "Confirm",
                            color = if (pendingPin != null) Primary else Color(0xFFAAAAAA)
                        )
                    }
                }
            }


            pendingPin?.let {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = Primary,
                            radius = 16f,
                            center = Offset(size.width / 2, size.height / 2)
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 8f,
                            center = Offset(size.width / 2, size.height / 2)
                        )
                    }
                    Text(
                        "📍 Pinned!",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 100.dp)
                            .background(White, RoundedCornerShape(20.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 13.sp,
                        color = Primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}