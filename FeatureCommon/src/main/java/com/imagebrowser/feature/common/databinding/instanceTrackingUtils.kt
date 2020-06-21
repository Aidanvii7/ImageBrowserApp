package com.imagebrowser.feature.common.databinding

import android.view.View
import androidx.annotation.IdRes
import androidx.databinding.adapters.ListenerUtil

inline fun <V : View, I> V.trackInstance(
    newInstance: I?,
    @IdRes instanceResId: Int,
    onDetached: V.(I) -> Unit = {},
    onAttached: V.(I) -> Unit = {}
) {
    ListenerUtil.trackListener(this, newInstance, instanceResId).let { oldInstance ->
        if (oldInstance !== newInstance) {
            oldInstance?.let { onDetached(oldInstance) }
            newInstance?.let { onAttached(newInstance) }
        }
    }
}

inline fun <V : View, I> V.trackValue(
    newValue: I?,
    @IdRes valueResId: Int,
    onNewValue: V.(I) -> Unit = {},
    onOldValue: V.(I) -> Unit = {}
) {
    ListenerUtil.trackListener(this, newValue, valueResId).let { oldValue ->
        if (oldValue !== newValue) {
            oldValue?.let { onOldValue(oldValue) }
            newValue?.let { onNewValue(newValue) }
        }
    }
}

fun <Value> View.getTrackedValue(@IdRes valueResId: Int): Value? =
    ListenerUtil.getListener<Value>(this, valueResId)
