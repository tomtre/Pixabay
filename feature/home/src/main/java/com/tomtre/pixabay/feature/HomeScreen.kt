package com.tomtre.pixabay.feature

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.tomtre.pixabay.core.model.DomainError
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.ui.AppScreen
import com.tomtre.pixabay.feature.ui.ImageListItem

@Composable
internal fun HomeRoute(
    onNavigateToDetails: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val imageItems = viewModel.items.collectAsLazyPagingItems()

    LaunchedEffect(uiState.navigateToDetails) {
        uiState.navigateToDetails?.let {
            onNavigateToDetails(it)
            viewModel.clearNavigation()
        }
    }

    AppScreen {
        HomeScreen(
            uiState = uiState,
            imageItems = imageItems,
            onImageItemClick = viewModel::onImageItemClick,
            onSearchQueryValueChanged = viewModel::onSearchQueryTextChanged
        )
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeState,
    imageItems: LazyPagingItems<Image>,
    onImageItemClick: (Int) -> Unit,
    onSearchQueryValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Button(onClick = { imageItems.refresh() }) {
            Text(text = "Refresh (invalidate)")
        }
        OutlinedTextField(
            value = uiState.querySearch,
            onValueChange = { onSearchQueryValueChanged(it) })

        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = modifier.fillMaxSize()) {
            if (imageItems.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imageItems.loadState.prepend is LoadState.Loading) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                    items(
                        count = imageItems.itemCount,
                        key = imageItems.itemKey { it.id }
                    ) { index ->
                        imageItems[index]?.let { image ->
                            ImageListItem(
                                previewUrl = image.previewURL,
                                userName = image.userName,
                                tags = image.tags,
                                modifier = modifier.clickable { onImageItemClick(image.id) }
                            )
                        }
                    }
                    if (imageItems.loadState.append is LoadState.Loading) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }

            // TODO testing, rewrite
            val loadState = imageItems.loadState
            val context = LocalContext.current
            LaunchedEffect(key1 = loadState) {
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let { errorLoadState ->
                    if (errorLoadState.error is DomainError) {
                        val message = when (val customError = errorLoadState.error) {
                            is DomainError.ApiError -> customError.apiMessage
                            is DomainError.UnknownError -> context.getString(R.string.error_network)
                            else -> ""
                        }
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
//    HomeScreen(HomeState(isLoading = true), {}, {})
}
