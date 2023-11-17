package com.tomtre.pixabay.core.ui.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    private data class DynamicString(val value: String) : UiText
    private class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText

    companion object {
        fun of(value: String): UiText = DynamicString(value)
        fun of(@StringRes resId: Int, vararg args: Any): UiText = StringResource(resId, args)
    }

    @Suppress("TopLevelComposableFunctions")
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id = resId, args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, args)
        }
    }
}
