package com.tomtre.pixabay.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tomtre.pixabay.feature.imagedetails.navigation.ImageDetailsArgs
import com.tomtre.pixabay.feature.imagedetails.navigation.imageDetailsScreen
import com.tomtre.pixabay.feature.imagedetails.navigation.navigateToImageDetails
import com.tomtre.pixabay.feature.imagelist.navigation.imageListNavigationRoute
import com.tomtre.pixabay.feature.imagelist.navigation.imageListScreen

@Composable
fun PixabayNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = imageListNavigationRoute,
    ) {
        imageListScreen(onNavigateToDetails = { navController.navigateToImageDetails(ImageDetailsArgs(it)) })
        imageDetailsScreen()
    }
}
