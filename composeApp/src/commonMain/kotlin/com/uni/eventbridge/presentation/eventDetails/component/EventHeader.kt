package com.uni.eventbridge.presentation.eventDetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.Secondary
import com.uni.eventbridge.presentation.eventDetails.EventDetailsUiState

@Composable
fun EventHeader(
    category: EventDetailsUiState.CategoryUiState,
    title: String,
    organizer: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (category.name.isNotBlank()) {
            Box(
                modifier = Modifier.background(
                    shape = RoundedCornerShape(50),
                    color = Secondary,
                )
            ) {
                Text(
                    text = category.name,
                    color = Primary,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = "Organized by ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = organizer,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}