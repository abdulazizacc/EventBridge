package com.uni.eventbridge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.uni.eventbridge.presentation.auth.AuthViewModel
import com.uni.eventbridge.presentation.auth.LoginScreen
import com.uni.eventbridge.presentation.createEvent.CreateEventScreen
import com.uni.eventbridge.presentation.eventDetails.EventDetailsScreen
import com.uni.eventbridge.presentation.events.EventsScreen
import com.uni.eventbridge.presentation.home.HomeScreen
import com.uni.eventbridge.presentation.map.MapNavigationScreen
import com.uni.eventbridge.presentation.profile.ProfileScreen
import com.uni.eventbridge.presentation.search.SearchScreen
import com.uni.eventbridge.presentation.splashScreen.SplashScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = koinViewModel()
) {

    val authState by authViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(authState.isAuthenticated, authState.isLoading) {
        if (!authState.isLoading) {
            val destination = if (authState.isAuthenticated) {
                Route.Home.toNavString()
            } else {
                Route.Login.toNavString()
            }

            navController.navigate(destination) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Route.AuthGraph.toNavString(),
        modifier = modifier,
    ) {
        navigation(
            startDestination = Route.Splash.toNavString(),
            route = Route.AuthGraph.toNavString()
        ) {
            composable(Route.Splash.toNavString()) {
                SplashScreen()
            }

            composable(Route.Login.toNavString()) {
                LoginScreen()
            }
        }

        navigation(
            startDestination = Route.Home.toNavString(),
            route = Route.MainGraph.toNavString()
        ) {
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

            composable(Route.CreateEvent.toNavString()) {
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
                ProfileScreen(
                    onNavigateToSignIn = {
                        navController.navigate(Route.AuthGraph.toNavString()) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                )
            }

            composable("navigation_map/{lat}/{lon}") { backStackEntry ->
                val lat = backStackEntry.arguments?.getString("lat")?.toDouble() ?: return@composable
                val lon = backStackEntry.arguments?.getString("lon")?.toDouble() ?: return@composable
                MapNavigationScreen(
                    eventLat = lat,
                    eventLon = lon,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("event_details/{eventId}") { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId") ?: return@composable
                EventDetailsScreen(
                    eventId = eventId.toLong(),
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToMap = { lat, lon ->
                        navController.navigate("navigation_map/$lat/$lon")
                    }
                )
            }
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