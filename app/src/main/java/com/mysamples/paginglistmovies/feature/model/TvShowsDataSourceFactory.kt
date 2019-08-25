package com.mysamples.paginglistmovies.feature.model

import androidx.paging.DataSource


class TvShowsDataSourceFactory(val tvShowDataSource: TvShowsDataSource) :
    DataSource.Factory<Int, TvShow>() {

    override fun create(): DataSource<Int, TvShow> = tvShowDataSource
}
