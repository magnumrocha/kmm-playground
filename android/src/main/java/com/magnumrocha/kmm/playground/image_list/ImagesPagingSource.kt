package com.magnumrocha.kmm.playground.image_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.magnumrocha.kmm.playground.picsum.PicsumImage

class ImagesPagingSource(
    private val getPicsumImages: suspend (page: Int, size: Int) -> List<PicsumImage>
) : PagingSource<Int, PicsumImage>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicsumImage> =
        try {
            val page = params.key ?: 1
            val result = getPicsumImages.invoke(page, params.loadSize)
            LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, PicsumImage>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
