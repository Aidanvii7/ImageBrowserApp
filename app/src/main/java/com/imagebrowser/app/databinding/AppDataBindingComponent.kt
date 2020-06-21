package com.imagebrowser.app.databinding

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.imagebrowser.feature.common.databinding.ImageViewBindingAdapters

data class AppDataBindingComponent(
    private val imageViewBindingAdapters: ImageViewBindingAdapters
) : DataBindingComponent {
    fun makeDefaultComponent() {
        DataBindingUtil.setDefaultComponent(this)
    }

    override fun getImageViewBindingAdapters() = imageViewBindingAdapters
}