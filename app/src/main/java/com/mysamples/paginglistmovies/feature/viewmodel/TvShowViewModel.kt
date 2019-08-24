package com.mysamples.paginglistmovies.feature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mysamples.paginglistmovies.feature.model.TvShow
import com.mysamples.paginglistmovies.feature.model.TvShowsDataSourceFactory


class TvShowViewModel(
    private val showsDataSourceFactory: TvShowsDataSourceFactory,
    private val livePagedListBuilder: LivePagedListBuilder<Int, TvShow>
) : ViewModel() {

    private lateinit var showsLiveData: LiveData<PagedList<TvShow>>

    fun tvShowLiveData() = showsLiveData

    init {
        initLiveData()
    }

    private fun initLiveData() {
        showsLiveData = livePagedListBuilder.build()
    }

    override fun onCleared() {
        super.onCleared()
        showsDataSourceFactory.tvShowDataSource.clear()
    }

}