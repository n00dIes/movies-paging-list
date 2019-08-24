package com.mysamples.paginglistmovies.feature.model

import androidx.paging.ItemKeyedDataSource
import com.mysamples.paginglistmovies.network.TvShowApiInteraface
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable


class TvShowsDataSource(
    private val apiInteraface: TvShowApiInteraface,
    private val mapper: TvShowMapper,
    private val io: Scheduler,
    private val main: Scheduler
) : ItemKeyedDataSource<Int, TvShow>() {

    private var pageNumber = 1
    private val compositeDisposable = CompositeDisposable()


    override fun getKey(item: TvShow): Int = pageNumber

    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<TvShow>
    ) {
        compositeDisposable.add(
            apiInteraface.getPopular(params.requestedInitialKey?.let { it } ?: pageNumber)
                .subscribeOn(io)
                .map { response -> mapper.map(response.results) }
                .observeOn(main)
                .subscribe({ shows -> onShowsFetched(shows, callback) }, { onError(it) })
        )
    }

    private fun onShowsFetched(shows: List<TvShow>, callback: LoadInitialCallback<TvShow>) {
        pageNumber++
        callback.onResult(shows)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<TvShow>) {
        compositeDisposable.add(
            apiInteraface.getPopular(params.key)
                .subscribeOn(io)
                .map { response -> mapper.map(response.results) }
                .observeOn(main)
                .subscribe({ shows -> onMoreShowsFetched(shows, callback) }, { onError(it) })
        )
    }

    private fun onMoreShowsFetched(shows: List<TvShow>, callback: LoadCallback<TvShow>) {
        pageNumber++
        callback.onResult(shows)
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<TvShow>) {
        // nothing to do here
    }

    private fun onError(throwable: Throwable) {}

    fun clear() {
        compositeDisposable.clear()
        pageNumber = 1
    }


}