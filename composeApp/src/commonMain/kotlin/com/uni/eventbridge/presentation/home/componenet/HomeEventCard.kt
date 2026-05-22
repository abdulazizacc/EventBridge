package com.uni.eventbridge.presentation.home.componenet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.uni.eventbridge.presentation.common.component.PrimaryButton
import com.uni.eventbridge.presentation.common.component.theme.Red
import com.uni.eventbridge.presentation.common.component.theme.SlateGray
import com.uni.eventbridge.presentation.common.component.theme.SurfaceDark
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_location
import eventbridge.composeapp.generated.resources.im_banner_image
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun HomeEventCard(
    imageUrl: String? = null,
    imageRes: DrawableResource = Res.drawable.im_banner_image,
    date: String,
    title: String,
    location: String,
    category: String? = null,
    isJoined: Boolean = false,
    isFull: Boolean = false,
    isJoinInProgress: Boolean = false,
    onClick: () -> Unit = {},
    onJoinClick: () -> Unit = {},
    onLeaveClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val joinEnabled = !isFull || isJoined
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(191.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick,
                    ),
            ) {
                if (!imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    )
                } else {
                    Image(
                        painter = painterResource(imageRes),
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    )
                }

                category?.let {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = it.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF2979FF),
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onClick,
                        )
                ) {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.labelMedium,
                        color = Red,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = SurfaceDark,
                    )

                    Row(
                        modifier = Modifier.padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_location),
                            contentDescription = null,
                            tint = SlateGray,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = location,
                            style = MaterialTheme.typography.bodyMedium,
                            color = SlateGray,
                        )
                    }
                }

                PrimaryButton(
                    onClick = {
                        if (isJoined) onLeaveClick() else onJoinClick()
                    },
                    label = when {
                        isJoinInProgress -> "…"
                        isJoined -> "Joined ✓"
                        else -> "Join Now →"
                    },
                    enabled = joinEnabled && !isJoinInProgress,
                )
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    HomeEventCard(
        imageUrl = null,
        imageRes = Res.drawable.im_banner_image,
        date = "FRI, OCT 25 • 6:00 PM",
        title = "Annual Spring Hackathon",
        location = "Student Union Hall, Main Campus",
        category = "Trending",
        onJoinClick = { /* navigate */ }
    )
}
