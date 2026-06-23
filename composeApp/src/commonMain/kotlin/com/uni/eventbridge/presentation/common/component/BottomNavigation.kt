package com.uni.eventbridge.presentation.common.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uni.eventbridge.presentation.common.component.modifier.noRippleClickable
import com.uni.eventbridge.presentation.common.component.theme.Primary
import com.uni.eventbridge.presentation.common.component.theme.Secondary
import com.uni.eventbridge.presentation.common.component.theme.SlateGray
import com.uni.eventbridge.presentation.common.component.theme.White
import com.uni.eventbridge.presentation.navigation.BottomNavigationRoute
import com.uni.eventbridge.presentation.navigation.toNavString
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route
        ?.substringBefore("?")


    val navItems = listOf(
        BottomNavigationRoute.Home,
        BottomNavigationRoute.Explore,
        BottomNavigationRoute.Events,
        BottomNavigationRoute.Account
    )

    val isVisible = currentRoute in listOf(
        BottomNavigationRoute.Home.route.toNavString(),
        BottomNavigationRoute.Explore.route.toNavString(),
        BottomNavigationRoute.Events.route.toNavString(),
        BottomNavigationRoute.Account.route.toNavString()
    )
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    ) {

        Column(modifier.fillMaxWidth().navigationBarsPadding()) {
            HorizontalDivider(thickness = 1.dp, color = Secondary)
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                navItems.forEach { item ->
                    val isSelected = currentRoute == item.route.toNavString()

                    NavItem(
                        isSelected = isSelected,
                        iconRes = item.iconRes,
                        label = item.label,
                        onClick = {
                            if (!isSelected) {
                                navController.navigate(item.route.toNavString()) {
                                    navController.graph.startDestinationRoute?.let {
                                        popUpTo(it) { saveState = true }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NavItem(
    iconRes: DrawableResource,
    isSelected: Boolean,
    label: String,
    onClick: () -> Unit,
) {
    val animatedColor = animateColorAsState(
        targetValue = if (isSelected) Primary else SlateGray,
        animationSpec = tween(durationMillis = 250),
        label = "NavItemColor"
    )

    Column(
        modifier = Modifier
            .size(48.dp)
            .noRippleClickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = label,
            tint = animatedColor.value,
            modifier = Modifier.size(24.dp).padding(bottom = 4.dp)
        )
        Text(
            text = label,
            color = if (isSelected) Primary else SlateGray,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
        )

    }
}
