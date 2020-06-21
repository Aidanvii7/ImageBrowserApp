package com.imagebrowser.feature.common.databinding

import android.os.Parcelable

val defaultAreItemsSame: (BindableAdapterItem, BindableAdapterItem) -> Boolean = { oldItem, newItem ->
    oldItem == newItem
}

class AdapterBinder<Item : BindableAdapterItem>(
    private val areItemsTheSame: ((oldItem: Item, newItem: Item) -> Boolean) = defaultAreItemsSame,
    private val areContentsTheSame: ((oldItem: Item, newItem: Item) -> Boolean) = defaultAreItemsSame
) {
    internal var layoutManagerState: Parcelable? = null

    internal val adapter: BindingPagingDataAdapter<Item>
        get() = BindingPagingDataAdapter(
            BindingPagingDataAdapter.Builder(
                areItemsTheSame = areItemsTheSame,
                areContentsTheSame = areContentsTheSame
            )
        )
}
