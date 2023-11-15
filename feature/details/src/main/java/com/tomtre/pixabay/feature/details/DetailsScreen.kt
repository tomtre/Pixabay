package com.tomtre.pixabay.feature.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tomtre.pixabay.core.ui.AppScreen

@Composable
internal fun DetailsRoute() {
    AppScreen {
        DetailsScreen()
    }
}

@Composable
private fun DetailsScreen() {
    Text(text = "DetailsScreen")
}

@Preview
@Composable
private fun PreviewDetailsScreen() {
    DetailsScreen()
}
