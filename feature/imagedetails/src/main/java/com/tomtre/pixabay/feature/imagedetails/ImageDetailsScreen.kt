package com.tomtre.pixabay.feature.imagedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomtre.pixabay.core.designsystem.component.DynamicAsyncImage
import com.tomtre.pixabay.core.designsystem.component.PixabayProgressIndicator
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.ui.AppScreen
import com.tomtre.pixabay.core.ui.util.UiText

@Composable
internal fun ImageDetailsRoute(
    viewModel: ImageDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    AppScreen {
        ImageDetailsScreen(uiState)
    }
}

@Composable
private fun ImageDetailsScreen(uiState: ImageDetailsState) {
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PixabayProgressIndicator()
        }
    }

    uiState.imageDetails?.let { imageDetails ->
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                DynamicAsyncImage(
                    imageUrl = imageDetails.largeImageURL,
                    modifier = Modifier
                        .size(300.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = stringResource(id = R.string.image_details_user_name))
                Text(text = imageDetails.user, modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = stringResource(id = R.string.image_details_tags))
                Text(text = imageDetails.tags, modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = stringResource(id = R.string.image_details_likes))
                Text(text = imageDetails.likes.toString(), modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = stringResource(id = R.string.image_details_downloads))
                Text(text = imageDetails.downloads.toString(), modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = stringResource(id = R.string.image_details_comments))
                Text(text = imageDetails.comments.toString(), modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
    uiState.error?.let {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = it.asString())
        }
    }
}

@Preview
@Composable
private fun PreviewDetailsScreen_Loaded() {
    val imageDetails = ImageDetails(
        id = 0,
        largeImageURL = "url",
        user = "tom",
        tags = "window cat sky",
        likes = 55,
        downloads = 4534,
        comments = 44
    )
    AppScreen {
        ImageDetailsScreen(ImageDetailsState(imageDetails = imageDetails))
    }
}

@Preview
@Composable
private fun PreviewDetailsScreen_Error() {
    AppScreen {
        ImageDetailsScreen(ImageDetailsState(error = UiText.of(R.string.error_loading_image_details)))
    }
}

@Preview
@Composable
private fun PreviewDetailsScreen_Loading() {
    AppScreen {
        ImageDetailsScreen(ImageDetailsState(isLoading = true))
    }
}
