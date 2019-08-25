package com.mysamples.paginglistmovies.feature.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mysamples.paginglistmovies.feature.model.TvShow
import com.mysamples.paginglistmovies.feature.model.TvShowDataSourceFactory
import com.mysamples.paginglistmovies.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
    private lateinit var observer: Observer<PagedList<TvShow>>

    private val liveData = MutableLiveData<PagedList<TvShow>>()

    @Before
    fun setUp() {
        `when`(pageListBuilder.build()).thenReturn(liveData)

        underTest = TvShowViewModel(factory, pageListBuilder)

    }

    @Test
    fun observeViewModel() {
        val pagedList: PagedList<TvShow> = mock()
        liveData.value = pagedList

        underTest.tvShowLiveData().observeForever(observer)

        verify(observer).onChanged(pagedList)
    }
}