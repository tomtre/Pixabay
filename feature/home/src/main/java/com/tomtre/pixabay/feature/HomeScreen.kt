package com.tomtre.pixabay.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomtre.pixabay.core.ui.AppScreen
import com.tomtre.pixabay.feature.ui.ImageListItem
import com.tomtre.pixabay.feature.ui.PullRefreshLazyList

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
        HomeScreen(uiState, viewModel::onImageItemClick, viewModel::refresh)
    }

}

@Composable
private fun HomeScreen(
    uiState: HomeState,
    onImageItemClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.errorMessage == null) {
        PullRefreshLazyList(isLoading = uiState.isLoading, onRefresh = onRefresh, modifier = modifier) {
            items(uiState.images, key = { it.id }) { image ->
                ImageListItem(
                    previewUrl = image.previewURL,
                    userName = image.userName,
                    tags = image.tags,
                    modifier = Modifier.clickable { onImageItemClick(image.id) }
                )
                Divider()
            }
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
            Text(text = uiState.errorMessage.asString())
            Button(onClick = onRefresh, modifier = Modifier.padding(top = 10.dp)) {
                Text(stringResource(id = R.string.refresh))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen(HomeState(isLoading = true), {}, {})
}
