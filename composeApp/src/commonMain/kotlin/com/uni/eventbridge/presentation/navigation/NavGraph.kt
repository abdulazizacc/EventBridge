package com.uni.eventbridge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uni.eventbridge.presentation.eventDetails.EventDetailsScreen
import com.uni.eventbridge.presentation.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.toNavString(),
        modifier = modifier,
    ) {
        composable(Route.Home.toNavString()) {
            HomeScreen(
                onNavigateToEventDetail = { eventId ->
                    navController.navigate(Route.EventDetails(eventId.toString()).toNavString())
                }
            )
        }

        composable(Route.Explore.toNavString()) {
            // TODO: ExploreScreen()
        }

        composable(Route.Account.toNavString()) {
            // TODO: AccountScreen()
        }

        composable("event_details/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: return@composable
            EventDetailsScreen(
                eventId = eventId.toLong(),
                onNavigateBack = { navController.navigateUp() },
            )
        }
    }
}