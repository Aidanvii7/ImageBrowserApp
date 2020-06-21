package com.imagebrowser.feature.common.databinding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import com.imagebrowser.feature.common.R

@BindingAdapter("lifecycleOwner")
fun View.bindLifecycleOwner(lifecycleOwner: LifecycleOwner?) {
    trackInstance(
        newInstance = lifecycleOwner,
        instanceResId = R.id.lifecycle_owner
    )
}

fun View.getRequiredLifecycleOwner(): LifecycleOwner =
    getLifecycleOwner() ?: throw IllegalStateException("No LifecycleOwner can be found in view hierarchy")

tailrec fun View.getLifecycleOwner(): LifecycleOwner? =
    getTrackedValue<LifecycleOwner>(R.id.lifecycle_owner) ?: (parent as? ViewGroup)?.getLifecycleOwner()