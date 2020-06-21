package com.imagebrowser.feature.common.databinding

import androidx.annotation.LayoutRes
import androidx.databinding.library.baseAdapters.BR

interface BindableAdapterItem {
    @get:LayoutRes
    val layoutId: Int
    val bindingId: Int get() = BR.viewModel
    val lazyBindableItem: Lazy<Any?>
        get() = lazy(LazyThreadSafetyMode.NONE) { this@BindableAdapterItem }
}