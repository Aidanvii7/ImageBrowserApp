package com.imagebrowser.feature.images

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.imagebrowser.common.CoroutineDispatchers
import com.imagebrowser.feature.images.core.implementation.BuildConfig
import com.imagebrowser.feature.images.data.api.PixabayApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class ImageRepositoryImpl(
    private val service: PixabayApiService,
    private val coroutineDispatchers: CoroutineDispatchers
) : ImageRepository {

    override val imageFlow: Flow<PagingData<ImageInfo>>
        get() = Pager(PagingConfig(BuildConfig.PIXABAY_PAGE_SIZE)) { ImageDataSource(service) }.flow

    override suspend fun getImageDetails(imageId: Int): ImageDetails? =
        withContext(coroutineDispatchers.io) {
            service.getImageDetails(
                key = BuildConfig.PIXABAY_API_KEY,
                imageId = imageId
            ).image?.toImageDetails()
        }
}

