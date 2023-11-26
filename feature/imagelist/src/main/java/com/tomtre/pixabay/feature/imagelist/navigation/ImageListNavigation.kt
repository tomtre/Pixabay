package com.tomtre.pixabay.feature.imagelist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tomtre.pixabay.feature.imagelist.ImageListRoute

@Suppress("TopLevelPropertyNaming")
const val imageListNavigationRoute = "image_list_navigation_route"

fun NavController.navigateToImageList(navOptions: NavOptions? = null) {
    this.navigate(imageListNavigationRoute, navOptions)
}

fun NavGraphBuilder.imageListScreen(onNavigateToDetails: (Int) -> Unit) {
    composable(route = imageListNavigationRoute) {
        ImageListRoute(onNavigateToDetails = onNavigateToDetails)
    }
}
