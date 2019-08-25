package com.mysamples.paginglistmovies.feature.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.mysamples.paginglistmovies.network.TvShowApiInteraface
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import java.util.concurrent.TimeUnit


private const val LOG_TAG = "TvShowsDataSource"

class TvShowsDataSource(
    private val apiInterface: TvShowApiInteraface,
    private val mapper: TvShowMapper,
    private val io: Scheduler,
    private val main: Scheduler
) : ItemKeyedDataSource<Int, TvShow>() {

    private var pageNumber = 1
    private val compositeDisposable = CompositeDisposable()
    private var retryCompletable: Action? = null

    val dataStateLiveData = MutableLiveData<DataState>()

    override fun getKey(item: TvShow): Int = pageNumber

    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<TvShow>
    ) {
        compositeDisposable.add(
            apiInterface.getPopular(params.requestedInitialKey?.let { it } ?: pageNumber)
                .subscribeOn(io)
                .doOnSubscribe { dataStateLiveData.postValue(DataState.INIITIAL_LOADING) }
                .map { response -> mapper.map(response.results) }
                .delay(5, TimeUnit.SECONDS)
                .observeOn(main)
                .subscribe({ onShowsFetched(it, callback) },
                    {
                        onLoadError(it, Action { loadInitial(params, callback) })
                    })
        )
    }

    private fun onShowsFetched(shows: List<TvShow>, callback: LoadInitialCallback<TvShow>) {
        pageNumber++
        dataStateLiveData.value = DataState.SUCCESS
        callback.onResult(shows)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<TvShow>) {
        compositeDisposable.add(
            apiInterface.getPopular(params.key)
                .subscribeOn(io)
                .doOnSubscribe { dataStateLiveData.postValue(DataState.PAGE_LOADING) }
                .map { response -> mapper.map(response.results) }
                .observeOn(main)
                .subscribe({ onMoreShowsFetched(it, callback) },
                    {
                        onLoadError(it, Action { loadAfter(params, callback) })
                    })
        )
    }

    private fun onLoadError(throwable: Throwable, action: Action) {
        Log.e(LOG_TAG, throwable.message, throwable);
        setRetry(action)
        dataStateLiveData.value = DataState.ERROR
    }

    private fun onMoreShowsFetched(shows: List<TvShow>, callback: LoadCallback<TvShow>) {
        pageNumber++
        dataStateLiveData.value = DataState.SUCCESS
        callback.onResult(shows)
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<TvShow>) {
        // nothing to do here
    }


    fun clear() {
        compositeDisposable.clear()
        pageNumber = 1
    }

    private fun setRetry(action: Action?) {
        retryCompletable = action

    }

    fun retry() {
        retryCompletable?.run()
    }


}