package com.mysamples.paginglistmovies.feature.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mysamples.paginglistmovies.feature.TvShowModule


class TvShowDataSourceFactory :
    DataSource.Factory<Int, TvShow>() {

    val tvShowDataSourceLiveData = MutableLiveData<TvShowDataSource>()

    override fun create(): DataSource<Int, TvShow> {
        val tvShowDataSource = TvShowModule.dataSource()
        tvShowDataSourceLiveData.postValue(tvShowDataSource)
        return tvShowDataSource

    }
}
