@file:Suppress("TopLevelPropertyNaming")

package com.tomtre.pixabay.feature.imagedetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tomtre.pixabay.feature.imagedetails.ImageDetailsRoute

const val imageDetailsNavigationRoute = "image_details_navigation_route"
const val imageIdArg = "image_id"

fun NavController.navigateToImageDetails(imageDetailsArgs: ImageDetailsArgs, navOptions: NavOptions? = null) {
    this.navigate("$imageDetailsNavigationRoute/${imageDetailsArgs.imageId}", navOptions)
}

data class ImageDetailsArgs(val imageId: Int) {
    constructor(savedStateHandle: SavedStateHandle) : this(imageId = checkNotNull(savedStateHandle[imageIdArg]))
}

fun NavGraphBuilder.imageDetailsScreen() {
    composable(
        route = "$imageDetailsNavigationRoute/{$imageIdArg}",
        arguments = listOf(navArgument(imageIdArg) { type = NavType.IntType })
    ) {
        ImageDetailsRoute()
    }
}
