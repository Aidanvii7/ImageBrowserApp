package com.imagebrowser.feature.common.databinding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.imagebrowser.feature.common.R
import com.imagebrowser.feature.common.widget.AspectRatioImageView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@BindingAdapter("aspectRatioFlow")
internal fun AspectRatioImageView.bind(aspectRatioFlow: Flow<Float>?) {
    trackValue(
        newValue = aspectRatioFlow,
        valueResId = R.id.aspectratio_imageview_binding_data,
        onOldValue = { /* TODO anything? */ },
        onNewValue = { bindAspectRatioFlow(getRequiredLifecycleOwner(), it) }
    )
}

private fun AspectRatioImageView.bindAspectRatioFlow(
    lifecycleOwner: LifecycleOwner,
    aspectRatioFlow: Flow<Float>
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        aspectRatioFlow.collectLatest { aspectRatio = it }
    }
}