package com.imagebrowser.feature.images.di

import android.app.Application
import com.imagebrowser.feature.images.ImageInfo
import com.imagebrowser.feature.images.presentation.browse.ImagesViewModel
import com.imagebrowser.feature.images.presentation.detail.ImageDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FeatureBrowseImagesPresentationModule {
    fun get() = module {
        viewModel {
            ImagesViewModel(
                imageRepository = get()
            )
        }

        viewModel { (imageInfo: ImageInfo) ->
            ImageDetailViewModel(
                application = get(),
                imageInfo = imageInfo,
                imageRepository = get(),
                coroutineDispatchers = get()
            )
        }
    }
}