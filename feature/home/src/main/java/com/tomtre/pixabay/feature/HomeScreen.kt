package com.tomtre.pixabay.feature

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomtre.pixabay.core.ui.AppScreen

@Composable
internal fun HomeRoute(
    onNavigateToDetails: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToDetails) {
        uiState.navigateToDetails?.let {
            onNavigateToDetails(it)
            viewModel.clearNavigation()
        }
    }

    AppScreen {
        HomeScreen(uiState, viewModel::onImageItemClick)
    }
}

@Composable
private fun HomeScreen(uiState: HomeState, onImageItemClick: (Int) -> Unit) {
    Text(text = "HomeScreen")
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen(HomeState(), {})
}
