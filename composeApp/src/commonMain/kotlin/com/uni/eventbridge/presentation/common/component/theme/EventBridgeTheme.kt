package com.uni.eventbridge.presentation.common.component.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EventBridgeLightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = White,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = Primary,
    secondary = Secondary,
    onSecondary = SlateGray,
    background = Secondary,
    onBackground = SlateGray,
    surface = White,
    onSurface = SlateGray,
    surfaceContainerLowest = White,
    surfaceContainerLow = Secondary,
    surfaceContainer = Secondary,
    surfaceContainerHigh = Secondary,
    surfaceContainerHighest = Secondary,
    error = Red,
    onError = White,
)

@Composable
fun EventBridgeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EventBridgeLightColorScheme,
        content = content,
    )
}
