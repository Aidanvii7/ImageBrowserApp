package com.imagebrowser.feature.images

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    val imageFlow: Flow<PagingData<ImageInfo>>
    suspend fun getImageDetails(imageId: Int): ImageDetails?
}