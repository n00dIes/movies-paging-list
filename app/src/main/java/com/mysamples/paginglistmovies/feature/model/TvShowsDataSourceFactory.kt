package com.mysamples.paginglistmovies.feature.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource


class TvShowsDataSourceFactory(val tvShowDataSource: TvShowsDataSource) :
    DataSource.Factory<Int, TvShow>() {

    private val tvShowsDataSourceLiveData = MutableLiveData<TvShowsDataSource>()

    override fun create(): DataSource<Int, TvShow> {
        val usersDataSource = tvShowDataSource
        tvShowsDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }

}