package com.uni.eventbridge.presentation.profile.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uni.eventbridge.presentation.common.component.theme.White

@Composable
fun TermOfService(
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = White,
        shape = RoundedCornerShape(20.dp),
        text = {
            Text(
                text = "All events on this platform comply with Iraqi laws and Tikrit University regulations regarding the types of events they may host.",
                fontSize = 18.sp,
                color = Color(0xFF4C4C4C),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A1A2E),
                )
            }
        },
    )
}