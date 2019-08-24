package com.mysamples.paginglistmovies.feature.model

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