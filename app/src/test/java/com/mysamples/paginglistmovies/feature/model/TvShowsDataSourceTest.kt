package com.mysamples.paginglistmovies.feature.model

import com.mysamples.paginglistmovies.network.TvShowApiInteraface
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsDataSourceTest {

    private lateinit var underTest: TvShowsDataSource

    @Mock
    private lateinit var mapper: TvShowMapper

    @Mock
    private lateinit var apiInteraface: TvShowApiInteraface

    @Before
    fun setUp() {
        underTest = TvShowsDataSource(
            apiInteraface,
            mapper,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun getKey() {

    }

    @Test
    fun loadInitial() {
    }

    @Test
    fun loadAfter() {
    }
}