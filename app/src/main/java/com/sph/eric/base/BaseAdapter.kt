package com.sph.eric.base

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sph.eric.R
import com.sph.eric.http.RequestResult
import com.sph.eric.util.ViewBindings

open class BaseAdapter :
    BaseQuickAdapter<BaseAdapter.MultiItem<Any>, BaseViewHolder>(View.NO_ID, mutableListOf()) {

    private val viewTypeBlocks = SparseArray<ViewTypeBlock<Any, BaseViewHolder>>()

    fun <T> addItemType(
        @LayoutRes layoutResId: Int,
        itemType: Int = layoutResId,
        converter: BaseViewHolder.(T) -> Unit = {}
    ) = putViewTypeBlock(itemType, layoutResId, converter, null)

    inline fun <T, reified VB : ViewBinding> addItemType(
        itemType: Int,
        noinline viewBinding: (ViewGroup) -> VB = { ViewBindings.reflectInflate(it) },
        noinline converter: ViewBindingHolder<VB>.(T) -> Unit = {}
    ) = putViewTypeBlock(itemType, View.NO_ID, converter, viewBinding)

    fun <E> setItems(items: Collection<E>, itemType: Int = View.NO_ID) = apply {
        setNewInstance(items.map { multiItem(itemType, it) }.toMutableList())
    }

    fun <E> addItems(index: Int, data: Collection<E>, itemType: Int = View.NO_ID) = apply {
        addData(index, data.map { multiItem(itemType, it) })
    }

    fun <E> addItems(items: Collection<E>, itemType: Int = View.NO_ID) = apply {
        addData(items.map { multiItem(itemType, it) })
    }

    fun <E> setItem(index: Int, data: E, itemType: Int = View.NO_ID) = apply {
        setData(index, multiItem(itemType, data))
    }

    fun <E> addItem(index: Int, data: E, itemType: Int = View.NO_ID) = apply {
        addData(index, multiItem(itemType, data))
    }

    fun <E> addItem(data: E, itemType: Int = View.NO_ID) = apply {
        addData(multiItem(itemType, data))
    }

    fun <E> getItemData(@IntRange(from = 0) position: Int) =
        if (position >= 0 && position < data.size) getItem(position).data as E else null

    fun <E> multiItem(itemViewType: Int, data: Collection<E> = mutableListOf()) =
        data.map { multiItem(itemViewType, it) }

    override fun getDefItemViewType(position: Int) = data[position].itemType

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int) =
        viewTypeBlocks[viewType]?.let { block ->
            block.viewBinding?.let { ViewBindingHolder(it.invoke(parent)) }
                ?: createBaseViewHolder(parent, block.layoutResId)
        }
            ?: throw IllegalArgumentException("ViewType: $viewType found layoutResId，please use addItemType() first!")

    override fun convert(holder: BaseViewHolder, item: MultiItem<Any>) {
        viewTypeBlocks[holder.itemViewType]?.converter?.invoke(holder, item.data)
    }

    fun <T : Any, VH : BaseViewHolder> putViewTypeBlock(
        itemType: Int,
        @LayoutRes layoutResId: Int,
        converter: VH.(T) -> Unit,
        viewBinding: ((ViewGroup) -> ViewBinding)?
    ) {
        viewTypeBlocks.put(
            itemType,
            ViewTypeBlock(
                itemType,
                layoutResId,
                converter as BaseViewHolder.(Any) -> Unit,
                viewBinding
            )
        )
    }

    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        swipeRefreshLayout = recyclerView.parent as? SwipeRefreshLayout
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        swipeRefreshLayout = null
    }

    fun onFailure() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout!!.isRefreshing = false
        }
    }

    fun <E> onResult(list: List<E>, itemType: Int = View.NO_ID) {
        var append = true
        if (swipeRefreshLayout != null) {
            append = swipeRefreshLayout!!.isRefreshing
            if (append) {
                swipeRefreshLayout!!.isRefreshing = false
            }
        }
        onResult(list, append, itemType)
    }

    fun <E> onResult(list: List<E>, append: Boolean, itemType: Int = View.NO_ID) {
        if (append) {
            addItems(list, itemType)
        } else {
            setItems(list, itemType)
        }
    }

    data class ViewTypeBlock<T : Any, VH : BaseViewHolder>(
        val itemType: Int,
        @LayoutRes val layoutResId: Int,
        val converter: VH.(T) -> Unit,
        val viewBinding: ((ViewGroup) -> ViewBinding)?
    )

    open class MultiItem<T>(override val itemType: Int, open val data: T) : MultiItemEntity
}

class ViewBindingHolder<VB : ViewBinding>(val viewBinding: VB) : BaseViewHolder(viewBinding.root)

fun baseAdapter(init: BaseAdapter.() -> Unit) = BaseAdapter().apply {
    init(this)
}

inline fun <reified T> baseAdapter(
    @LayoutRes layout: Int,
    list: List<T> = listOf(),
    noinline converter: BaseViewHolder.(T) -> Unit
) = baseAdapter {
    addItemType(layout, layout, converter)
    setNewInstance(list.map { multiItem(layout, it) }.toMutableList())
}

inline fun <T, reified VB : ViewBinding> baseAdapter(
    list: MutableList<T> = mutableListOf(),
    noinline viewBinding: (ViewGroup) -> VB = { ViewBindings.reflectInflate(it) },
    noinline converter: ViewBindingHolder<VB>.(T) -> Unit
) = baseAdapter {
    addItemType(View.NO_ID, viewBinding, converter)
    setNewInstance(list.map { multiItem(View.NO_ID, it) }.toMutableList())
}

fun <T> multiItem(itemViewType: Int, data: T? = null) =
    BaseAdapter.MultiItem(itemViewType, data ?: Unit)


fun BaseAdapter.onFailureWithLayout(
    failure: RequestResult.Error,
    failureLayout: Int = R.layout.layout_empty_no_net,
) {
    onFailure()
    if (failure.isConnectionFailure) setEmptyView(failureLayout)
}

fun <E> BaseAdapter.onResultWithLayout(
    list: List<E>,
    viewType: Int = View.NO_ID,
    emptyLayout: Int = R.layout.layout_empty_no_data,
) {
    onResult(list, viewType)
    if (data.isEmpty()) setEmptyView(emptyLayout)
}