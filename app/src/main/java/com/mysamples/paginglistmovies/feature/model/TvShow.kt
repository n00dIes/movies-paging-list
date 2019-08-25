package com.mysamples.paginglistmovies.feature.model


data class TvShow(
    val originalName: String,
    val id: Int,
    val name: String,
    val voteCount: Int,
    val voteAverage: Float,
    val imageUrl: String,
    val overview: String
)