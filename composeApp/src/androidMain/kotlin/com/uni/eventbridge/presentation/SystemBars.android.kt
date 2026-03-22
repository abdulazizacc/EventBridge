package com.uni.eventbridge.presentation

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private fun Color.isApproximatelyLightSurface(): Boolean {
    return 0.2126f * red + 0.7152f * green + 0.0722f * blue > 0.5f
}

@Composable
actual fun EventBridgeSystemBars() {
    val view = LocalView.current
    val colorScheme = MaterialTheme.colorScheme
    SideEffect {
        if (view.isInEditMode) return@SideEffect
        val window = (view.context as Activity).window
        val controller = WindowCompat.getInsetsController(window, view)
        val useDarkIcons = colorScheme.background.isApproximatelyLightSurface()
        controller.isAppearanceLightStatusBars = useDarkIcons
        controller.isAppearanceLightNavigationBars = useDarkIcons
    }
}