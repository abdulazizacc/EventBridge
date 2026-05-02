package com.uni.eventbridge.presentation.eventDetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.uni.eventbridge.presentation.common.component.theme.White

@Composable
fun AttendeesRow(
    avatarUrls: List<String>,
    onClick: () -> Unit = {}
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(color =
                Color(0xFFF1F5F9),
                shape = RoundedCornerShape(16.dp)
            )
            .wrapContentSize()
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            avatarUrls.take(3).forEachIndexed { index, url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .offset(x = (index * 22).dp)
                        .clip(CircleShape)
                        .border(2.dp, White, CircleShape),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "students are going",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp)
        )
    }
}