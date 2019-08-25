package com.mysamples.paginglistmovies.feature.model

import androidx.paging.DataSource


class TvShowDataSourceFactory(val tvShowDataSource: TvShowDataSource) :
    DataSource.Factory<Int, TvShow>() {

    override fun create(): DataSource<Int, TvShow> = tvShowDataSource
}
