package com.uni.eventbridge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uni.eventbridge.presentation.auth.AuthViewModel
import com.uni.eventbridge.presentation.auth.LoginScreen
import com.uni.eventbridge.presentation.createEvent.CreateEventScreen
import com.uni.eventbridge.presentation.eventDetails.EventDetailsScreen
import com.uni.eventbridge.presentation.events.EventsScreen
import com.uni.eventbridge.presentation.home.HomeScreen
import com.uni.eventbridge.presentation.search.SearchScreen
import com.uni.eventbridge.presentation.splashScreen.SplashScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash.toNavString(),
        modifier = modifier,
    ) {
        composable(Route.Splash.toNavString()) {
            val viewModel: AuthViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(uiState.isLoading, uiState.isAuthenticated) {
                if (!uiState.isLoading) {
                    val destination = if (uiState.isAuthenticated) {
                        Route.Home.toNavString()
                    } else {
                        Route.Login.toNavString()
                    }
                    navController.navigate(destination) {
                        popUpTo(Route.Splash.toNavString()) { inclusive = true }
                    }
                }
            }

            SplashScreen()
        }
        composable(Route.Login.toNavString()) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Route.Home.toNavString()) {
                        popUpTo(Route.Login.toNavString()) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Route.Home.toNavString()) {
            HomeScreen(
                onNavigateToEventDetail = { eventId ->
                    navController.navigateToEventDetails(eventId)
                }
            )
        }

        composable(Route.Explore.toNavString()) {
            SearchScreen(
                onNavigateToEventDetail = { eventId ->
                    navController.navigateToEventDetails(eventId)
                }
            )
        }

        composable(Route.Events.toNavString()) {
            EventsScreen(
                onNavigateToEventDetail = { eventId ->
                    navController.navigateToEventDetails(eventId)
                },
                onCreateEventClicked = {
                    navController.navigate(Route.CreateEvent.toNavString())
                }
            )
        }

        composable (Route.CreateEvent.toNavString()){
            CreateEventScreen(
                onNavigateBack = { navController.navigateUp() },
                onEventPublished = {
                    navController.navigate(Route.Events.toNavString()) {
                        popUpTo(Route.Home.toNavString()) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Route.Account.toNavString()) {
            // TODO: AccountScreen()
        }

        composable("event_details/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: return@composable
            EventDetailsScreen(
                eventId = eventId.toLong(),
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}

private fun NavHostController.navigateToEventDetails(eventId: Long) {
    navigate("event_details/$eventId") {
        launchSingleTop = true
        popUpTo("event_details/{eventId}") {
            inclusive = true
            saveState = false
        }
    }
}