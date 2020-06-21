package com.imagebrowser.feature.common.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.imagebrowser.common.isNotNullAndNotEmpty
import com.imagebrowser.feature.common.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlin.contracts.ExperimentalContracts

abstract class ImageViewBindingAdapters {

    @BindingAdapter("imageUrlFlow")
    internal fun ImageView.bind(imageUrlFlow: Flow<String>?) {
        trackValue(
            newValue = imageUrlFlow,
            valueResId = R.id.imageview_binding_data,
            onOldValue = {
                cancelPendingRequest()
                setImageDrawable(null)
            },
            onNewValue = { bindImageUrlFlow(getRequiredLifecycleOwner(), it) }
        )
    }

    private fun ImageView.bindImageUrlFlow(
        lifecycleOwner: LifecycleOwner,
        imageUrlFlow: Flow<String>
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            imageUrlFlow.collectLatest { imageUrl ->
                cancelPendingRequest()
                loadImage(imageUrl, crossFade = true)
            }
        }
    }

    @ExperimentalContracts
    @BindingAdapter(
        "imageUrl",
        "crossFade",
        requireAll = false
    )
    fun ImageView.bind(
        imageUrl: String?,
        crossFade: Boolean
    ) {
        trackValue(
            newValue = imageUrlFor(imageUrl),
            valueResId = R.id.image_view_url,
            onOldValue = {
                cancelPendingRequest()
                if (imageUrl == null) {
                    setImageDrawable(null)
                }
            },
            onNewValue = { loadImage(it, crossFade) }
        )
    }

    @ExperimentalContracts
    private fun imageUrlFor(imageUrl: String?): String? =
        if (imageUrl.isNotNullAndNotEmpty()) imageUrl else null

    protected abstract fun ImageView.loadImage(imageUrl: String, crossFade: Boolean)

    protected abstract fun ImageView.cancelPendingRequest()
}