package com.uni.eventbridge.designSystem


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EventBridgeScaffold(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    topBar:  (@Composable () -> Unit)? = null,
    bottomBar:  (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable (BoxScope.() -> Unit),
) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(color = Color(0xFFF6F6F8)),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.matchParentSize(),
        ) {

            topBar?.invoke()

            Box {
                Crossfade(targetState = isLoading) { isLoading ->
                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                        }
                    } else {
                        content()
                    }
                }
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .imePadding(),
            horizontalAlignment = Alignment.End,
        ) {
            floatingActionButton?.let {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = if (bottomBar != null) 8.dp else 16.dp),
                ) {
                    it.invoke()
                }
            }

            bottomBar?.invoke()
        }
    }
}

