package com.tomtre.pixabay.feature.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomtre.core.designsystem.component.DynamicAsyncImage

@Composable
fun ImageListItem(previewUrl: String, userName: String, tags: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(top = 5.dp, bottom = 5.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        DynamicAsyncImage(
            imageUrl = previewUrl,
            modifier = Modifier.size(80.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp)
        ) {
            Text(text = userName)
            Text(text = tags)
        }
    }
}
