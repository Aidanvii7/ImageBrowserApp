package com.imagebrowser.feature.images.presentation.browse

import com.imagebrowser.feature.common.databinding.BindableAdapterItem
import com.imagebrowser.common.unsafeLazy
import com.imagebrowser.feature.images.ImageInfo
import com.imagebrowser.feature.images.presentation.R

internal class ImageInfoAdapterItem(
    private val imageInfo: ImageInfo
) : BindableAdapterItem {
    override val layoutId
        get() = R.layout.item_image_info

    override val lazyBindableItem = unsafeLazy { ImageInfoViewModel(imageInfo) }
}