package com.tomtre.pixabay.core.database.util

import androidx.room.withTransaction
import com.tomtre.pixabay.core.database.PixabayDatabase
import java.util.concurrent.Callable
import javax.inject.Inject

class DatabaseTransactionProvider @Inject constructor(
    private val pixabayDatabase: PixabayDatabase
) {

    fun runInTransaction(body: Runnable) {
        pixabayDatabase.runInTransaction(body)
    }

    fun <V> runInTransaction(body: Callable<V>): V {
        return pixabayDatabase.runInTransaction(body)
    }

    suspend fun <R> withTransaction(block: suspend () -> R): R {
        return pixabayDatabase.withTransaction(block)
    }
}
