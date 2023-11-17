package com.tomtre.pixabay.core.model

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class ApiError(val message: String) : Result<Nothing>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
}
