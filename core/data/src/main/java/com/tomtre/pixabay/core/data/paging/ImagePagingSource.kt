package com.tomtre.pixabay.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tomtre.pixabay.core.data.repository.ImagesRepositoryImpl
import com.tomtre.pixabay.core.data.toDomainImage
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.network.NetworkDataSource
import timber.log.Timber

class ImagePagingSource(
    private val query: String?,
    private val networkDataSource: NetworkDataSource
) : PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        Timber.d("ImagePagingSource getRefreshKey() state: $state")

        state.anchorPosition?.let {
            Timber.d("ImagePagingSource getRefreshKey() closest page to position: ${state.closestPageToPosition(it)}")
        }

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        Timber.d("ImagePagingSource load() params: $params")

        val position = params.key ?: STARTING_PAGE_INDEX
        Timber.d("ImagePagingSource load() api call page: $position, loadSize: ${params.loadSize}")
        val networkResult = networkDataSource.getImages(query = query, page = position, loadSize = params.loadSize)
        val loadResult: LoadResult<Int, Image> = networkResult
            .fold(
                ifLeft = { LoadResult.Error(Exception()) },
                ifRight = { imageResponse ->
                    val items = imageResponse.hits
                    val nextKey = if (items.isEmpty()) {
                        null
                    } else {
                        // initial load size = 3 * NETWORK_PAGE_SIZE
                        // ensure we're not requesting duplicating items, at the 2nd request
                        position + (params.loadSize / ImagesRepositoryImpl.ITEMS_PER_PAGE)
                    }
                    LoadResult.Page(
                        data = items.toDomainImage(),
                        prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                        nextKey = nextKey
                    )
                }
            )
        Timber.d("ImagePagingSource load() load result: $loadResult")
        return loadResult
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
