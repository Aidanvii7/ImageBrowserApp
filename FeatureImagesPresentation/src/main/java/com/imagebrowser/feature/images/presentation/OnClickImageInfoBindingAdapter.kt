package com.imagebrowser.feature.images.presentation

import android.os.Build
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.imagebrowser.feature.images.ImageInfo
import com.imagebrowser.feature.images.presentation.browse.BrowseImagesFragmentDirections

@BindingAdapter("onClickImageInfo")
internal fun View.bindOnClickImageInfo(imageInfo: ImageInfo?) {
    setOnClickListener(
        imageInfo?.let {
            View.OnClickListener {
                when (id) {
                    R.id.image_info -> navigateFromBrowseImagesToImageDetail(imageInfo)
                    else -> throwUnknownViewId<ImageInfo>()
                }
            }
        }
    )
}

private fun View.navigateFromBrowseImagesToImageDetail(imageInfo: ImageInfo) {
    val directions = BrowseImagesFragmentDirections.actionDestinationBrowseImagesToDestinationImageDetail(imageInfo)
        findNavController().navigate(directions)
}

private inline fun <reified T> View.throwUnknownViewId(): Nothing =
    throw IllegalStateException("unknown view ID '$id' for type ${T::class.java.simpleName}")
