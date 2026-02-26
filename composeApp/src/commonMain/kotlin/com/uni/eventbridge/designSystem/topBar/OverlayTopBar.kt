package com.uni.eventbridge.designSystem.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_back
import eventbridge.composeapp.generated.resources.ic_home
import eventbridge.composeapp.generated.resources.ic_location
import org.jetbrains.compose.resources.painterResource

@Composable
fun OverlayTopBar(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    trailingIcon1: Painter? = null,
    onTrailing1Click: (() -> Unit)? = null,
    trailingIcon2: Painter? = null,
    onTrailing2Click: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
    ) {

        if (onBackClick != null) {
            CircleOverlayButton(
                painter = painterResource(Res.drawable.ic_back),
                onClick = onBackClick
            )
        } else {
            Spacer(Modifier.size(40.dp))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            trailingIcon1?.let {
                CircleOverlayButton(
                    painter = it,
                    onClick = onTrailing1Click
                )
            }

            trailingIcon2?.let {
                CircleOverlayButton(
                    painter = it,
                    onClick = onTrailing2Click
                )
            }
        }
    }
}

@Composable
private fun CircleOverlayButton(
    painter: Painter,
    onClick: (() -> Unit)?,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            }
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
private fun OverlayTopBarPreview() {
    MaterialTheme {
        OverlayTopBar(
            onBackClick = {},
            trailingIcon1 = painterResource(Res.drawable.ic_location),
            onTrailing1Click = {},
            trailingIcon2 = painterResource(Res.drawable.ic_home),
            onTrailing2Click = {}
        )
    }
}