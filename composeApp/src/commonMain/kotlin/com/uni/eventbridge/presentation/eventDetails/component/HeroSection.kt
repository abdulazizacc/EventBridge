package com.uni.eventbridge.presentation.eventDetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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

        Image(
            painter = painterResource(Res.drawable.im_banner_image),
            contentDescription = "title",
            contentScale = ContentScale.Crop,
        )

        OverlayTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            onBackClick = onBackClick,
            onTrailing1Click = onShareClick,
        )
    }
}