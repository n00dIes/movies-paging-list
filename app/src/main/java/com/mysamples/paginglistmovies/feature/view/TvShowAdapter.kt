package com.mysamples.paginglistmovies.feature.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mysamples.paginglistmovies.R
import com.mysamples.paginglistmovies.feature.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_tvshow.view.*

class TvShowAdapter() : PagedListAdapter<TvShow, RecyclerView.ViewHolder>(tvShowDiffCallback) {

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
        val view = layoutInflater.inflate(R.layout.list_item_tvshow, parent, false)
        return TvShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as TvShowViewHolder).bind(it) }
    }


    class TvShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(tvShow: TvShow) {
            itemView.tv_show_name.text = tvShow.name
            itemView.tv_show_overview.text = tvShow.overview
            Picasso.get().load(tvShow.imageUrl).into(itemView.movie_image)
        }


    }


}
