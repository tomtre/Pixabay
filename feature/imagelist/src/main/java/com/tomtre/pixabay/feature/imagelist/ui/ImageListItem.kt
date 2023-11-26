package com.tomtre.pixabay.feature.imagelist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tomtre.pixabay.core.designsystem.component.DynamicAsyncImage

@Composable
fun ImageListItem(previewUrl: String, userName: String, tags: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        DynamicAsyncImage(
            imageUrl = previewUrl,
            modifier = Modifier
                .size(80.dp)

        )
        Spacer(modifier = Modifier.width(8.dp))
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

@Preview
@Composable
private fun PreviewImageListItem() {
    ImageListItem(previewUrl = "url", userName = "user", tags = "tag1 tag2 tag3", modifier = Modifier.fillMaxWidth())
}
