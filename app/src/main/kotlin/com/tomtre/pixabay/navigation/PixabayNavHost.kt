package com.tomtre.pixabay.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tomtre.pixabay.feature.details.navigation.detailsScreen
import com.tomtre.pixabay.feature.details.navigation.navigateToDetails
import com.tomtre.pixabay.feature.navigation.homeNavigationRoute
import com.tomtre.pixabay.feature.navigation.homeScreen

@Composable
fun PixabayNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = homeNavigationRoute,
    ) {
        // todo handle an argument
        homeScreen(onNavigateToDetails = { navController.navigateToDetails() })
        detailsScreen()

    }
}

