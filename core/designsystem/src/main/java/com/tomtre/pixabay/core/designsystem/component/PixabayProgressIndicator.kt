package com.tomtre.pixabay.core.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp

@Composable
fun PixabayProgressIndicator() {
    val isLocalInspection = LocalInspectionMode.current
    if (isLocalInspection) {
        CircularProgressIndicator(progress = 0.8f, modifier = Modifier.size(64.dp))
    } else {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
    }
}
