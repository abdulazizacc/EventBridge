package com.uni.eventbridge.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.Secondary
import com.uni.eventbridge.presentation.common.component.theme.White

@Composable
@Preview
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: String = "Button",
    enabled: Boolean = true,
    verticalPadding: Int = 12
) {
    val backgroundColor = if (enabled) Primary else Secondary
    val textColor = if (enabled) White else White.copy(alpha = 0.6f)
        Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = backgroundColor, shape = RoundedCornerShape(verticalPadding.dp))
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}