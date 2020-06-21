package com.imagebrowser.feature.common.databinding

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView

class BindingPagingDataAdapter<Item : BindableAdapterItem>(
    builder: Builder<Item>
) : PagingDataAdapter<Item, BindingPagingDataAdapter.ViewHolder<Item>>(
    diffCallback = object : ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = builder.areItemsTheSame(oldItem, newItem)
        override fun areContentsTheSame(oldItem: Item, newItem: Item) = builder.areContentsTheSame(oldItem, newItem)
    }
) {

    class Builder<Item : BindableAdapterItem> internal constructor(
        internal val areItemsTheSame: (old: Item, new: Item) -> Boolean,
        internal val areContentsTheSame: (old: Item, new: Item) -> Boolean
    )

    class ViewHolder<Item : BindableAdapterItem> internal constructor(
        val bindingResourceId: Int,
        val viewDataBinding: ViewDataBinding
    ) : RecyclerView.ViewHolder(viewDataBinding.root) {
        var boundAdapterItem: Item? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Item> {
        val layoutResourceId = getLayoutId(viewType)
        val bindingResourceId = getBindingId(layoutResourceId)
        val viewDataBinding = parent.unattachedBindingOf<ViewDataBinding>(layoutResourceId)
        return createWith(bindingResourceId, viewDataBinding)
    }

    @LayoutRes
    private fun getLayoutId(viewType: Int): Int = viewType

    private val cachedBindingIds = SparseIntArray()

    private fun getBindingId(layoutId: Int): Int = cachedBindingIds.get(layoutId)

    override fun getItemViewType(position: Int): Int =
        getItem(position)?.let { adapterItem ->
            adapterItem.layoutId.also { layoutId ->
                cachedBindingIds.put(layoutId, adapterItem.bindingId)
            }
        } ?: throw IllegalStateException("Cannot determine view type for item at position: $position")

    private fun createWith(
        bindingResourceId: Int,
        viewDataBinding: ViewDataBinding
    ) = ViewHolder<Item>(bindingResourceId, viewDataBinding)

    override fun onBindViewHolder(holder: ViewHolder<Item>, position: Int) {
        getItem(position)?.let { adapterItem ->
            holder.boundAdapterItem = adapterItem
            val bindableItem = adapterItem.lazyBindableItem.value
            try {
                holder.viewDataBinding.setVariable(holder.bindingResourceId, bindableItem)
            } catch (classCastException: ClassCastException) {
                if (bindableItem != null)
                    holder.throwBindableItemWrongType(adapterItem, bindableItem, classCastException)
            }
            holder.viewDataBinding.executePendingBindings()
        }
    }

    private fun <Binding : ViewDataBinding> ViewGroup.unattachedBindingOf(@LayoutRes layoutResourceId: Int): Binding =
        DataBindingUtil.inflate<Binding>(LayoutInflater.from(context), layoutResourceId, this, false).also { viewDataBinding ->
            if (viewDataBinding == null) {
                throw IllegalStateException("provided layout resource was not a data binding layout")
            }
        }

    private fun ViewHolder<Item>.throwBindableItemWrongType(adapterItem: Item, bindableItem: Any, cause: ClassCastException): Nothing =
        throw IllegalArgumentException(
            "cannot set variable with type ${bindableItem::class.java.simpleName} on " +
                    "${viewDataBinding::class.java.simpleName} with binding variable ID " +
                    "provided by ${adapterItem::class.java.simpleName}.bindingId. " +
                    "Is ${adapterItem::class.java.simpleName}.layoutId correct?", cause
        )

}