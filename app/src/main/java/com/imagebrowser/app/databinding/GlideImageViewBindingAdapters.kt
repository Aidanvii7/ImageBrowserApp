package com.imagebrowser.app.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.imagebrowser.common.logger.logE
import com.imagebrowser.feature.common.databinding.ImageViewBindingAdapters
import com.imagebrowser.feature.common.utils.isNotActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import kotlin.contracts.ExperimentalContracts

object GlideImageViewBindingAdapters : ImageViewBindingAdapters() {

    private val crossFadeFactory = DrawableCrossFadeFactory
        .Builder()
        .setCrossFadeEnabled(true)
        .build()

    @ExperimentalContracts
    override fun ImageView.loadImage(imageUrl: String, crossFade: Boolean) {
        context.let { context ->
            if (context.isNotActivity() || !context.isDestroyed) {
                try {
                    Glide.with(context)
                        .load(imageUrl)
                        .applyTransition(crossFade)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                        .into(this)
                } catch (e: IllegalArgumentException) {
                    logE { e.toString() }
                }
            }
        }
    }

    private fun RequestBuilder<Drawable>.applyTransition(crossFade: Boolean): RequestBuilder<Drawable> =
        if (crossFade) transition(withCrossFade(crossFadeFactory)) else this

    @ExperimentalContracts
    override fun ImageView.cancelPendingRequest() {
        context.let { context ->
            if (context.isNotActivity() || !context.isDestroyed) {
                try {
                    Glide.with(context).clear(this)
                } catch (e: IllegalArgumentException) {
                    logE { e.toString() }
                }
            }
        }
    }
}