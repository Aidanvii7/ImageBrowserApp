package com.imagebrowser.feature.images.presentation.browse

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.imagebrowser.feature.common.databinding.AdapterBinder
import com.imagebrowser.feature.images.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ImagesViewModel(
    imageRepository: ImageRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val imageInfoAdapterItems: Flow<PagingData<ImageInfoAdapterItem>> =
        imageRepository.imageFlow.map { pagingData ->
            pagingData.map { imageInfo ->
                ImageInfoAdapterItem(imageInfo)
            }
        }

    val binder = AdapterBinder<ImageInfoAdapterItem>()
}