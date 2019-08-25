package com.mysamples.paginglistmovies.feature.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mysamples.paginglistmovies.R
import com.mysamples.paginglistmovies.feature.model.DataState
import com.mysamples.paginglistmovies.feature.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_tvshow.view.*

class TvShowAdapter :
    ExtraRowPagedListAdapter<DataState, TvShow, RecyclerView.ViewHolder>(tvShowDiffCallback) {

    companion object {
        val tvShowDiffCallback = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.list_item_tvshow -> {
                val view = layoutInflater.inflate(R.layout.list_item_tvshow, parent, false)
                TvShowViewHolder(view)
            }

            R.layout.list_item_progress -> {
                val view = layoutInflater.inflate(R.layout.list_item_progress, parent, false)
                ProgressViewHolder(view)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.list_item_tvshow -> (holder as TvShowViewHolder).bind(getItem(position))
            R.layout.list_item_progress -> (holder as ProgressViewHolder)
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (hasExtraRow() && position == itemCount - 1) {
            R.layout.list_item_progress
        } else {
            R.layout.list_item_tvshow
        }

    // private fun hasExtraRow() = dataState != null && dataState == DataState.PAGE_LOADING

    override fun hasExtraRow() = state != null && state == DataState.PAGE_LOADING

    internal class TvShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(tvShow: TvShow?) {
            itemView.tv_show_name.text = tvShow?.name
            itemView.tv_show_overview.text = tvShow?.overview
            itemView.tv_show_rating.text = tvShow?.voteAverage.toString()
            Picasso.get().load(tvShow?.imageUrl).into(itemView.movie_image)
        }
    }

    internal class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)


}
