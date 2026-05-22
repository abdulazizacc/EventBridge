package com.uni.eventbridge.presentation.common.component.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.presentation.common.component.modifier.noRippleClickable
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.SurfaceDark
import com.uni.eventbridge.presentation.common.component.theme.White
import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun DefaultTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 8.dp, vertical = 12.dp),
    ) {

        if (onBackClick != null) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = "Back",
                    tint = SurfaceDark
                )
            }
        }

        Text(
            text = title,
            color = SurfaceDark,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )

        if (actionLabel != null && onActionClick != null) {
            Text(
                text = actionLabel,
                color = Primary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
                    .noRippleClickable(
                        onClick = { onActionClick() }
                    )
            )
        }
    }
}

@Preview
@Composable
private fun DefaultTopBarPreview() {
    MaterialTheme {
        DefaultTopBar(
            title = "Event Details",
            onBackClick = {},
            actionLabel = "Edit",
        )
    }
}
