package com.tomtre.pixabay.feature

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import com.tomtre.pixabay.core.model.CustomError
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
        HomeScreen(uiState, imageItems, viewModel::onImageItemClick, viewModel::refresh)
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeState,
    imageItems: LazyPagingItems<Image>,
    onImageItemClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Button(onClick = { imageItems.refresh() }) {
            Text(text = "Refresh (invalidate)")
        }

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
                    if (errorLoadState.error is CustomError) {
                        val message = when (val customError = errorLoadState.error) {
                            is CustomError.ApiError -> customError.apiMessage
                            is CustomError.UnknownError -> context.getString(R.string.error_network)
                            else -> ""
                        }
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show()
                    }
                }
            }

//    if (uiState.errorMessage == null) {
//        PullRefreshLazyList(isLoading = uiState.isLoading, onRefresh = onRefresh, modifier = modifier) {
//            items(uiState.images, key = { it.id }) { image ->
//                ImageListItem(
//                    previewUrl = image.previewURL,
//                    userName = image.userName,
//                    tags = image.tags,
//                    modifier = Modifier.clickable { onImageItemClick(image.id) }
//                )
//                Divider()
//            }
//        }
//    } else {
//        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
//            Text(text = uiState.errorMessage.asString())
//            Button(onClick = onRefresh, modifier = Modifier.padding(top = 10.dp)) {
//                Text(stringResource(id = R.string.refresh))
//            }
//        }
//    }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
//    HomeScreen(HomeState(isLoading = true), {}, {})
}
