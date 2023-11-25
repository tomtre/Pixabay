package com.tomtre.pixabay.core.model

sealed class CustomError : Throwable() {
    data object UnknownError : CustomError()
    data class ApiError(val apiMessage: String) : CustomError()
}
