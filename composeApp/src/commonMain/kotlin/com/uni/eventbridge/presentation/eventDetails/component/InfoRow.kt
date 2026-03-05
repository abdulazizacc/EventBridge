package com.uni.eventbridge.presentation.eventDetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.designSystem.theme.Primary
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_calendder
import eventbridge.composeapp.generated.resources.ic_hour
import eventbridge.composeapp.generated.resources.ic_location
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun EventInfoRows(
    date: String,
    timeRange: String,
    venueName: String,
    venueDetail: String,
    onAddToSchedule: () -> Unit = {},
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        InfoRow(
            iconRes = Res.drawable.ic_calendder,
            primary = date,
        )

        InfoRow(
            iconRes = Res.drawable.ic_hour,
            primary = timeRange,
            secondaryClickable = "Add to your schedule",
            onSecondaryClick = onAddToSchedule,
        )

        InfoRow(
            iconRes = Res.drawable.ic_location,
            primary = venueName,
            secondary = venueDetail,
        )
    }
}
@Composable
private fun InfoRow(
    iconRes: DrawableResource,
    primary: String,
    secondary: String? = null,
    secondaryClickable: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFFF5F7FE),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center,

            ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.padding(all = 24.dp).size(20.dp),
            )
        }

        Column {
            Text(
                text = primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
            )
            secondary?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

        }
    }
}
