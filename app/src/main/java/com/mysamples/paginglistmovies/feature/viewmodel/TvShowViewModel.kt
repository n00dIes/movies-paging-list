package com.mysamples.paginglistmovies.feature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mysamples.paginglistmovies.feature.model.DataState
import com.mysamples.paginglistmovies.feature.model.TvShow
import com.mysamples.paginglistmovies.feature.model.TvShowDataSourceFactory


class TvShowViewModel(
    private val showDataSourceFactory: TvShowDataSourceFactory,
    private val livePagedListBuilder: LivePagedListBuilder<Int, TvShow>
) : ViewModel() {

    private lateinit var showsLiveData: LiveData<PagedList<TvShow>>
    private lateinit var dataLoadingState: LiveData<DataState>

    init {
        initLiveData()
    }

    private fun initLiveData() {
        dataLoadingState =
            Transformations.switchMap(showDataSourceFactory.tvShowDataSourceLiveData) { dataSource -> dataSource.dataStateLiveData }

        showsLiveData = livePagedListBuilder.build()
    }

    fun retry() = showDataSourceFactory.tvShowDataSourceLiveData.value?.retry()

    fun refresh() = showDataSourceFactory.tvShowDataSourceLiveData.value?.clear()

    fun tvShowLiveData() = showsLiveData

    fun dataLoadingState() = dataLoadingState

    override fun onCleared() {
        super.onCleared()
        showDataSourceFactory.tvShowDataSourceLiveData.value?.clear()
    }
}