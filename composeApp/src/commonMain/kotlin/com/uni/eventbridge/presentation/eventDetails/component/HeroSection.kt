package com.uni.eventbridge.presentation.eventDetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.uni.eventbridge.designSystem.topBar.OverlayTopBar
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.im_banner_image
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeroSection(
    imageUrl: String,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
    ) {
        if (imageUrl.isNotBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Event banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE8EEF4)),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(Res.drawable.im_banner_image),
                    contentDescription = "Event banner placeholder",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        OverlayTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            onBackClick = onBackClick,
            onTrailing1Click = onShareClick,
        )
    }
}