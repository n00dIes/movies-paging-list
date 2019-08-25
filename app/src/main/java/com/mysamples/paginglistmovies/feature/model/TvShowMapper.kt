package com.mysamples.paginglistmovies.feature.model

import com.mysamples.paginglistmovies.network.NetworkModule
import com.mysamples.paginglistmovies.network.model.TvShowModel


class TvShowMapper {

    fun map(list: List<TvShowModel>): List<TvShow> {
        val tvShows = mutableListOf<TvShow>()
        for (item in list) {
            tvShows.add(map(item))
        }

        return tvShows
    }

    private fun map(item: TvShowModel) = TvShow(
        item.original_name,
        item.id,
        item.name,
        item.vote_count.toInt(),
        item.vote_average,
        NetworkModule.IMG_BASE_URL + item.poster_path,
        item.overview
    )
}