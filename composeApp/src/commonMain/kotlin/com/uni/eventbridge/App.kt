package com.uni.eventbridge

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.uni.eventbridge.presentation.EventBridgeSystemBars
import com.uni.eventbridge.presentation.common.component.BottomNavigation
import com.uni.eventbridge.presentation.common.component.theme.EventBridgeTheme
import com.uni.eventbridge.presentation.navigation.NavGraph

@Composable
@Preview
fun App() {
    EventBridgeTheme {
        EventBridgeSystemBars()
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(navController = navController)
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}