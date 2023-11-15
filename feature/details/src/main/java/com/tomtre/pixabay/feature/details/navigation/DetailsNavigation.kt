package com.tomtre.pixabay.feature.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tomtre.pixabay.feature.details.DetailsRoute

@Suppress("TopLevelPropertyNaming")
const val detailsNavigationRoute = "details_navigation_route"

fun NavController.navigateToDetails(navOptions: NavOptions? = null) {
    this.navigate(detailsNavigationRoute, navOptions)
}

fun NavGraphBuilder.detailsScreen() {
    composable(route = detailsNavigationRoute) {
        DetailsRoute()
    }
}
