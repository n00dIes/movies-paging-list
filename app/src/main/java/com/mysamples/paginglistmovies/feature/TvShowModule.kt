package com.mysamples.paginglistmovies.feature

import com.mysamples.paginglistmovies.feature.model.TvShowMapper
import com.mysamples.paginglistmovies.feature.model.TvShowsDataSource
import com.mysamples.paginglistmovies.network.NetworkModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object TvShowModule {

    private fun dataSource() = TvShowsDataSource(
        NetworkModule.tvShowsApiService(),
        mapper(),
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    )

    private fun mapper() = TvShowMapper()


}