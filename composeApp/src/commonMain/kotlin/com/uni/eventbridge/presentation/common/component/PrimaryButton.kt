package com.uni.eventbridge.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.White

@Composable
@Preview
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    label: String = "Button",
    verticalPadding: Int = 12,
    backgroundColor: Color = Primary,
) {
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = backgroundColor, shape = RoundedCornerShape(verticalPadding.dp))
            .clickable( onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = White,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}
