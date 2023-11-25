package com.tomtre.pixabay.core.data.paging

import com.tomtre.pixabay.core.database.dao.ImagesDao
import com.tomtre.pixabay.core.database.dao.RemoteKeysDao
import com.tomtre.pixabay.core.database.util.DatabaseTransactionProvider
import com.tomtre.pixabay.core.network.NetworkDataSource
import javax.inject.Inject

class ImageRemoteMediatorFactory @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val imagesDao: ImagesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val databaseTransactionProvider: DatabaseTransactionProvider
) {

    fun getRemoteMediator(query: String?) =
        ImageRemoteMediator(
            query = query,
            networkDataSource = networkDataSource,
            imagesDao = imagesDao,
            remoteKeysDao = remoteKeysDao,
            databaseTransactionProvider = databaseTransactionProvider
        )
}
