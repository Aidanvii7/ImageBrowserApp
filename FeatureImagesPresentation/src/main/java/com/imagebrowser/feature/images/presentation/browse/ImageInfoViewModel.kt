package com.imagebrowser.feature.images.presentation.browse

import com.imagebrowser.feature.images.ImageInfo

internal class ImageInfoViewModel(val imageInfo: ImageInfo) {
    val previewUrl: String
        get() = imageInfo.previewUrl

    val username: String
        get() = imageInfo.username

    val aspectRatio: Float by lazy(LazyThreadSafetyMode.NONE) {
        imageInfo.previewSize.run {
            width.toFloat() / height.toFloat()
        }
    }
}