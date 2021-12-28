package com.magnumrocha.kmm.playground.image_list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.magnumrocha.kmm.playground.picsum.PicsumImage
import com.magnumrocha.kmm.playground.picsum.PicsumImagesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val picsumImagesApi: PicsumImagesApi
) : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 10
    }

    fun observeImages(): Flow<PagingData<PicsumImage>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagesPagingSource(::getImages) }
        ).flow

    private suspend fun getImages(page: Int, size: Int): List<PicsumImage> =
        withContext(Dispatchers.IO) {
            picsumImagesApi.get(page, size)
        }
}
