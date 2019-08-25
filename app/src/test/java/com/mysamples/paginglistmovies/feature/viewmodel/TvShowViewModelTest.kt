package com.mysamples.paginglistmovies.feature.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mysamples.paginglistmovies.feature.model.DataState
import com.mysamples.paginglistmovies.feature.model.TvShow
import com.mysamples.paginglistmovies.feature.model.TvShowDataSource
import com.mysamples.paginglistmovies.feature.model.TvShowDataSourceFactory
import com.mysamples.paginglistmovies.mock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var factory: TvShowDataSourceFactory

    @Mock
    private lateinit var pageListBuilder: LivePagedListBuilder<Int, TvShow>

    private lateinit var underTest: TvShowViewModel

    @Mock
    private lateinit var tvShowObserver: Observer<PagedList<TvShow>>

    @Mock
    private lateinit var dataStateObserver: Observer<DataState>

    @Mock
    private lateinit var dataSource: TvShowDataSource

    private val dataSourceLiveData = MutableLiveData<TvShowDataSource>()
    private val dataStateLiveDataState = MutableLiveData<DataState>()

    private val liveData = MutableLiveData<PagedList<TvShow>>()


    @Before
    fun setUp() {
        mockViewModelInit()

        underTest = TvShowViewModel(factory, pageListBuilder)
    }

    @After
    fun tearDown() {
        underTest.tvShowLiveData().removeObserver(tvShowObserver)
        underTest.dataLoadingState().removeObserver(dataStateObserver)

        verifyNoMoreInteractions(tvShowObserver, dataStateObserver)
    }

    private fun mockViewModelInit() {
        `when`(pageListBuilder.build()).thenReturn(liveData)
        `when`(factory.tvShowDataSourceLiveData).thenReturn(dataSourceLiveData)
    }

    @Test
    fun observeTvShowLiveData() {
        val pagedList: PagedList<TvShow> = mock()
        liveData.value = pagedList

        underTest.tvShowLiveData().observeForever(tvShowObserver)

        verify(tvShowObserver).onChanged(pagedList)
    }

    @Test
    fun observeDataStateLiveData() {
        `when`(dataSource.dataStateLiveData).thenReturn(dataStateLiveDataState)
        dataSourceLiveData.value = dataSource
        dataStateLiveDataState.value = DataState.PAGE_LOADING

        underTest.dataLoadingState().observeForever(dataStateObserver)

        verify(dataStateObserver).onChanged(DataState.PAGE_LOADING)
    }
}