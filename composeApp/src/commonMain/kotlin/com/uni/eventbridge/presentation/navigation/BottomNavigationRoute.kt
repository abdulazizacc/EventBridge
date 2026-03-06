package com.uni.eventbridge.presentation.navigation

import eventbridge.composeapp.generated.resources.Res
import eventbridge.composeapp.generated.resources.ic_account
import eventbridge.composeapp.generated.resources.ic_explore
import eventbridge.composeapp.generated.resources.ic_home
import eventbridge.composeapp.generated.resources.ic_my_events
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavigationRoute(
    val route: Route,
    val iconRes: DrawableResource,
    val label: String
) {
    data object Home : BottomNavigationRoute(
        route = Route.Home,
        iconRes = Res.drawable.ic_home,
        label = "Home"
    )

    data object Explore : BottomNavigationRoute(
        route = Route.Explore,
        iconRes = Res.drawable.ic_explore,
        label = "Explore"
    )

    data object Events : BottomNavigationRoute(
        route = Route.Events,
        iconRes = Res.drawable.ic_my_events,
        label = "Events"
    )
    data object Account : BottomNavigationRoute(
        route = Route.Account,
        iconRes = Res.drawable.ic_account,
        label = "Account"
    )
}