package com.tomtre.pixabay.ui.greeting

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

@Suppress("TopLevelPropertyNaming")
const val greetingRoute: String = "greeting_route"

fun NavGraphBuilder.greetingGraph() {
    composable(route = greetingRoute) {
        GreetingScreen()
    }
}
