package com.uni.eventbridge.presentation.eventDetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uni.eventbridge.designSystem.theme.Primary
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.im_map
import org.jetbrains.compose.resources.painterResource

@Composable
fun MapSection(
    onNavigateClick: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "Location",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onNavigateClick() },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.im_map),
                contentDescription = "Event location map",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.92f), RoundedCornerShape(50.dp))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    "Find event",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Primary,
                )
            }
        }
    }
}