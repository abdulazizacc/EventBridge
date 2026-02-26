package com.uni.eventbridge.designSystem.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uni.eventbridge.designSystem.theme.Primary
import com.uni.eventbridge.designSystem.theme.PrimaryContainer
import com.uni.eventbridge.designSystem.theme.SurfaceDark
import com.uni.eventbridge.designSystem.theme.White
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_explore
import org.jetbrains.compose.resources.painterResource

@Composable
fun BadgeTopBar(
    title: String,
    leadingIcon: Painter,
    modifier: Modifier = Modifier,
    onLeadingClick: (() -> Unit)? = null,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 16.dp, vertical = 14.dp),
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer)
                    .clickable(enabled = onLeadingClick != null) {
                        onLeadingClick?.invoke()
                    }
            ) {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = title,
                color = SurfaceDark,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFF1F5F9)
        )
    }
}

@Preview
@Composable
private fun BadgeTopBarPreview() {
    MaterialTheme {
        BadgeTopBar(
            title = "Discover",
            leadingIcon = painterResource(Res.drawable.ic_explore),
            onLeadingClick = {}
        )
    }
}