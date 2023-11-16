package com.tomtre.pixabay.core.network.util

val <T> RequestError<T>.response: T?
    get() = (this as? RequestError.Api<T>)?.response

val <T> RequestError<T>.exception: Throwable?
    get() = (this as? RequestError.Exception)?.throwable
