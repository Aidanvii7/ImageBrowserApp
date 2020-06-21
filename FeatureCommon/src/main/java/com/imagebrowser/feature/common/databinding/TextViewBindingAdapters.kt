package com.imagebrowser.feature.common.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.imagebrowser.feature.common.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@BindingAdapter("textFlow")
internal fun TextView.bind(textFlow: Flow<String>?) {
    trackValue(
        newValue = textFlow,
        valueResId = R.id.textview_binding_data,
        onOldValue = { /* TODO anything? */ },
        onNewValue = { bindTextFlow(getRequiredLifecycleOwner(), it) }
    )
}

private fun TextView.bindTextFlow(
    lifecycleOwner: LifecycleOwner,
    textFlow: Flow<String>
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        textFlow.collectLatest { text = it }
    }
}