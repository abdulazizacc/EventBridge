package com.uni.eventbridge.presentation.eventDetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.uni.eventbridge.designSystem.theme.Primary
import com.uni.eventbridge.designSystem.theme.Secondary
import com.uni.eventbridge.presentation.eventDetails.EventDetailsUiState
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.im_banner_image
import org.jetbrains.compose.resources.painterResource

@Composable
fun EventHeader(
    category: EventDetailsUiState.CategoryUiState,
    title: String,
    organizer: String,
    organizerAvatarUrl: String,
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
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                if (organizerAvatarUrl.isNotBlank()) {
                    AsyncImage(
                        model = organizerAvatarUrl,
                        contentDescription = "Organizer avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    Image(
                        painter = painterResource(Res.drawable.im_banner_image),
                        contentDescription = "Organizer avatar placeholder",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
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