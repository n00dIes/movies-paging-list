package com.mysamples.paginglistmovies.feature.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.ItemKeyedDataSource
import com.mysamples.paginglistmovies.aNetWorkResponse
import com.mysamples.paginglistmovies.aTvShow
import com.mysamples.paginglistmovies.network.TvShowApiInteraface
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsDataSourceTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var underTest: TvShowsDataSource

    @Mock
    private lateinit var mapper: TvShowMapper

    @Mock
    private lateinit var apiInteraface: TvShowApiInteraface

    @Mock
    private lateinit var initialCallback: ItemKeyedDataSource.LoadInitialCallback<TvShow>

    @Mock
    private lateinit var afterCallback: ItemKeyedDataSource.LoadCallback<TvShow>

    @Mock
    private lateinit var observer: Observer<DataState>

    private val currentKey = 5
    private val aResponse = aNetWorkResponse()
    private val expectedTvShows = listOf(aTvShow())

    @Before
    fun setUp() {

        underTest = TvShowsDataSource(
            apiInteraface,
            mapper,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )

        underTest.dataStateLiveData.observeForever(observer)
    }

    @After
    fun tearDown() {
        underTest.dataStateLiveData.removeObserver(observer)
        verifyNoMoreInteractions(afterCallback, initialCallback, observer)
    }

    @Test
    fun loadInitial() {
        val loadInitialParams = ItemKeyedDataSource.LoadInitialParams(currentKey, 10, false)

        `when`(apiInteraface.getPopular(currentKey)).thenReturn(Single.just(aResponse))
        `when`(mapper.map(aResponse.results)).thenReturn(expectedTvShows)

        underTest.loadInitial(loadInitialParams, initialCallback)

        verify(initialCallback).onResult(expectedTvShows)
        verify(observer).onChanged(DataState.INITIAL_LOADING)
        verify(observer).onChanged(DataState.SUCCESS)

    }

    @Test
    fun loadInitial_whenErrorThrown() {
        val throwable = Throwable()
        val loadInitialParams = ItemKeyedDataSource.LoadInitialParams(currentKey, 10, false)

        `when`(apiInteraface.getPopular(currentKey)).thenReturn(Single.error(throwable))

        underTest.loadInitial(loadInitialParams, initialCallback)

        verify(observer).onChanged(DataState.INITIAL_LOADING)
        verify(observer).onChanged(DataState.ERROR)
    }

    @Test
    fun loadAfter() {
        val loadParams = ItemKeyedDataSource.LoadParams(currentKey, 10)

        `when`(apiInteraface.getPopular(currentKey)).thenReturn(Single.just(aResponse))
        `when`(mapper.map(aResponse.results)).thenReturn(expectedTvShows)

        underTest.loadAfter(loadParams, afterCallback)

        verify(afterCallback).onResult(expectedTvShows)
        verify(observer).onChanged(DataState.PAGE_LOADING)
        verify(observer).onChanged(DataState.SUCCESS)
    }

    @Test
    fun loadAfter_whenErrorThrown() {
        val throwable = Throwable()
        val loadParams = ItemKeyedDataSource.LoadParams(currentKey, 10)

        `when`(apiInteraface.getPopular(currentKey)).thenReturn(Single.error(throwable))

        underTest.loadAfter(loadParams, afterCallback)

        verify(observer).onChanged(DataState.PAGE_LOADING)
        verify(observer).onChanged(DataState.ERROR)
    }
}