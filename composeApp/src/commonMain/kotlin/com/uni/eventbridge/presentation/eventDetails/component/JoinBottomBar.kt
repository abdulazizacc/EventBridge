package com.uni.eventbridge.presentation.eventDetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uni.eventbridge.designSystem.PrimaryButton
import com.uni.eventbridge.designSystem.theme.Secondary
import com.uni.eventbridge.designSystem.theme.White

@Composable
fun JoinBottomBar(
    isJoined: Boolean,
    onJoinClick: () -> Unit,
) {
    Column {
        HorizontalDivider(thickness = 1.dp, color = Secondary)
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(color = White)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .navigationBarsPadding(),
        ) {
            PrimaryButton(
                onClick = onJoinClick,
                label = if (isJoined) "Joined ✓" else "Join Now →",
                modifier = Modifier.align(Alignment.Center)
            )

        }
    }
}