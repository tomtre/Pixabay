package com.tomtre.pixabay.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tomtre.pixabay.feature.HomeRoute

@Suppress("TopLevelPropertyNaming")
const val homeNavigationRoute = "home_navigation_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(onNavigateToDetails: (Int) -> Unit) {
    composable(route = homeNavigationRoute) {
        HomeRoute(onNavigateToDetails = onNavigateToDetails)
    }
}
