package com.mysamples.paginglistmovies.feature

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mysamples.paginglistmovies.feature.model.TvShowMapper
import com.mysamples.paginglistmovies.feature.model.TvShowDataSource
import com.mysamples.paginglistmovies.feature.model.TvShowDataSourceFactory
import com.mysamples.paginglistmovies.feature.viewmodel.TvShowViewModel
import com.mysamples.paginglistmovies.network.NetworkModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object TvShowModule {

    private const val PAGE_SIZE = 20
    private const val INIT_LOAD_SIZE = PAGE_SIZE * 2

    fun viewModel(): TvShowViewModel {
        val dataSourceFactory = dataSourceFactory()
        return TvShowViewModel(
            dataSourceFactory,
            livePagedListBuilder(dataSourceFactory)
        )
    }

    private fun dataSourceFactory() = TvShowDataSourceFactory(dataSource())

    private fun dataSource() = TvShowDataSource(
        NetworkModule.tvShowsApiService(),
        mapper(),
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    )

    private fun livePagedListBuilder(dataSourceFactory: TvShowDataSourceFactory) =
        LivePagedListBuilder(dataSourceFactory, pagedListConfig())

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(INIT_LOAD_SIZE)
        .build()

    private fun mapper() = TvShowMapper()


}