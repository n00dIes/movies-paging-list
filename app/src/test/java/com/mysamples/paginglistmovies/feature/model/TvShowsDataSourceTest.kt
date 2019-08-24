package com.mysamples.paginglistmovies.feature.model

import androidx.paging.ItemKeyedDataSource
import com.mysamples.paginglistmovies.aNetWorkResponse
import com.mysamples.paginglistmovies.aTvShow
import com.mysamples.paginglistmovies.network.TvShowApiInteraface
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsDataSourceTest {

    private lateinit var underTest: TvShowsDataSource

    @Mock
    private lateinit var mapper: TvShowMapper

    @Mock
    private lateinit var apiInteraface: TvShowApiInteraface

    @Mock
    private lateinit var initialCallback: ItemKeyedDataSource.LoadInitialCallback<TvShow>
    @Mock
    private lateinit var afterCallback: ItemKeyedDataSource.LoadCallback<TvShow>

    private val currentKey = 5

    @Before
    fun setUp() {
        underTest = TvShowsDataSource(
            apiInteraface,
            mapper,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(afterCallback, initialCallback)
    }

    @Test
    fun loadInitial() {
        val aResponse = aNetWorkResponse()
        val expectedTvShows = listOf(aTvShow())
        val loadInitialParams = ItemKeyedDataSource.LoadInitialParams(currentKey, 10, false)
        `when`(apiInteraface.getPopular(currentKey)).thenReturn(Single.just(aResponse))
        `when`(mapper.map(aResponse.results)).thenReturn(expectedTvShows)

        underTest.loadInitial(loadInitialParams, initialCallback)

        verify(initialCallback).onResult(expectedTvShows)

    }

    @Test
    fun loadAfter() {
        val aResponse = aNetWorkResponse()
        val expectedTvShows = listOf(aTvShow())
        val loadParams = ItemKeyedDataSource.LoadParams(currentKey, 10)
        `when`(apiInteraface.getPopular(currentKey)).thenReturn(Single.just(aResponse))
        `when`(mapper.map(aResponse.results)).thenReturn(expectedTvShows)

        underTest.loadAfter(loadParams, afterCallback)

        verify(afterCallback).onResult(expectedTvShows)
    }
}