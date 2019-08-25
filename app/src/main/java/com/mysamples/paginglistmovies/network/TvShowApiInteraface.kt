package com.mysamples.paginglistmovies.network

import com.mysamples.paginglistmovies.network.model.PopularTvShowResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface TvShowApiInteraface {

    companion object {
        const val PATH_TO_POPULAR = "tv/popular"
        const val PAGE_QUERY_PARAM = "page"
    }

    @GET(PATH_TO_POPULAR)
    fun getPopular(@Query(PAGE_QUERY_PARAM) page: Int): Single<PopularTvShowResponse>

}