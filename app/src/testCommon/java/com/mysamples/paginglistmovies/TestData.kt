package com.mysamples.paginglistmovies

import com.mysamples.paginglistmovies.feature.model.TvShow
import com.mysamples.paginglistmovies.network.NetworkModule
import com.mysamples.paginglistmovies.network.model.PopularTvShowResponse
import com.mysamples.paginglistmovies.network.model.TvShowModel


fun aTvShowModel() = TvShowModel("Original Name", 10, "Name", 1200F, 7.2F, "/imageUrl", "Overview")

fun aNetWorkResponse() = PopularTvShowResponse(1, 200, 20, listOf(aTvShowModel()))

fun aTvShow() = TvShow(
    "Original Name",
    10,
    "Name",
    1200F,
    7.2F,
    NetworkModule.IMG_BASE_URL + "/imageUrl",
    "Overview"
)
