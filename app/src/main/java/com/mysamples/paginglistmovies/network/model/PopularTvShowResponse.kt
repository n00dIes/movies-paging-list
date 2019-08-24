package com.mysamples.paginglistmovies.network.model

data class PopularTvShowResponse(
    val page: Int,
    val total_result: Int,
    val total_pages: Int,
    val results: List<TvShows>
)

data class TvShows(
    val original_name: String,
    val id: Int,
    val name: String,
    val vote_count: Float,
    val vote_average: Float,
    val poster_path: String,
    val overview: String
)