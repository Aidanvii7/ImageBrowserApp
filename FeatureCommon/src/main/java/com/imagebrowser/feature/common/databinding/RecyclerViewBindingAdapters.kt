package com.imagebrowser.feature.common.databinding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.imagebrowser.feature.common.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

@BindingAdapter(
    "binder",
    "itemFlow",
    "autoScrollToTopOnNewDataFlow",
    "swipeRefreshLayout",
    requireAll = false
)
internal fun <Item : BindableAdapterItem> RecyclerView.bind(
    binder: AdapterBinder<Item>?,
    itemFlow: Flow<PagingData<Item>>?,
    autoScrollToTopOnNewDataFlow: Boolean?,
    swipeRefreshLayout: SwipeRefreshLayout?
) {
    tryRebind(binder)
    tryRebind(
        PagingBindingData(
            itemFlow = itemFlow,
            autoScrollToTopOnNewDataFlow = autoScrollToTopOnNewDataFlow ?: true,
            swipeRefreshLayout = swipeRefreshLayout
        )
    )
}

private data class PagingBindingData<Item : BindableAdapterItem>(
    val itemFlow: Flow<PagingData<Item>>?,
    val autoScrollToTopOnNewDataFlow: Boolean,
    val swipeRefreshLayout: SwipeRefreshLayout?
)

private fun <Item : BindableAdapterItem> RecyclerView.tryRebind(binder: AdapterBinder<Item>?) {
    trackInstance(
        newInstance = binder,
        instanceResId = R.id.list_binder,
        onDetached = { detachedBinder ->
            detachedBinder.layoutManagerState = layoutManager?.onSaveInstanceState()
            adapter = null
        },
        onAttached = { attachedBinder ->
            val freshAdapter = attachedBinder.adapter
            adapter = freshAdapter
            layoutManager = LinearLayoutManager(context).apply {
                onRestoreInstanceState(attachedBinder.layoutManagerState)
            }
        }
    )
}

private fun <Item : BindableAdapterItem> RecyclerView.tryRebind(pagingBindingData: PagingBindingData<Item>) {
    trackValue(
        newValue = pagingBindingData,
        valueResId = R.id.paging_binding_data,
        onOldValue = { /* TODO anything? */ },
        onNewValue = { (dataFlow, autoScrollToTopOnNewDataFlow, swipeRefreshLayout) ->
            val lifecycleOwner = getRequiredLifecycleOwner()
            if (dataFlow != null) {
                bindDataFlow(lifecycleOwner, dataFlow)
            }
            if (autoScrollToTopOnNewDataFlow) {
                bindAutoScrollToTopOnDataRefreshFlow<Item>(lifecycleOwner)
            }
            if (swipeRefreshLayout != null) {
                bindSwipeRefreshLayout<Item>(lifecycleOwner, swipeRefreshLayout)
            }
        }
    )
}

private fun <Item : BindableAdapterItem> RecyclerView.bindDataFlow(
    lifecycleOwner: LifecycleOwner,
    dataFlow: Flow<PagingData<Item>>
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        getBindingPagingDataAdapter<Item>()?.apply {
            dataFlow.collectLatest { pagingData ->
                submitData(pagingData)
            }
        }
    }
}

private fun <Item : BindableAdapterItem> RecyclerView.bindAutoScrollToTopOnDataRefreshFlow(lifecycleOwner: LifecycleOwner) {
    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        getBindingPagingDataAdapter<Item>()?.apply {
            dataRefreshFlow.collectLatest {
                scrollToPosition(0)
            }
        }
    }
}

private fun <Item : BindableAdapterItem> RecyclerView.bindSwipeRefreshLayout(
    lifecycleOwner: LifecycleOwner,
    swipeRefreshLayout: SwipeRefreshLayout
) {
    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        getBindingPagingDataAdapter<Item>()?.apply {
            swipeRefreshLayout.setOnRefreshListener { refresh() }
            loadStateFlow.map { it.refresh }.collectLatest { refreshState ->
                swipeRefreshLayout.isRefreshing = refreshState == Loading
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <Item : BindableAdapterItem> RecyclerView.getBindingPagingDataAdapter() = adapter as? BindingPagingDataAdapter<Item>
