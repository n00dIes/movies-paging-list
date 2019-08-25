package com.mysamples.paginglistmovies.feature.view

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class ExtraRowPagedListAdapter<S, T, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, VH>(diffCallback) {

    abstract fun hasExtraRow(): Boolean

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    var state: S? = null
        private set

    fun setDataState(newDataState: S?) {
        val previousState = state
        val hadExtraRow = hasExtraRow()
        this.state = newDataState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newDataState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}