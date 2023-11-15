package com.tomtre.pixabay.feature

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tomtre.pixabay.core.ui.AppScreen

@Composable
internal fun HomeRoute(
    onNavigateToDetails: (Int) -> Unit
) {
    AppScreen {
        HomeScreen()
    }
}

@Composable
private fun HomeScreen() {
    Text(text = "HomeScreen")
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen()
}
