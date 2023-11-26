package com.tomtre.pixabay.core.model

sealed class DomainError : Throwable() {
    data object UnknownError : DomainError()
    data class ApiError(val apiMessage: String) : DomainError()
}
