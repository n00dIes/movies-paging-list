package com.mysamples.paginglistmovies.feature.model

import com.mysamples.paginglistmovies.aNetWorkResponse
import com.mysamples.paginglistmovies.aTvShow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowMapperTest {

    private val underTest = TvShowMapper()

    @Test
    fun map() {
        val expected = listOf(aTvShow())

        val actual = underTest.map(aNetWorkResponse().results)

        assertNotNull(actual)
        assertEquals(1, actual.size)
        assertEquals(expected, actual)
    }

    @Test
    fun map_when_empty() {
        val expected = emptyList<TvShow>()

        val actual = underTest.map(emptyList())

        assertNotNull(actual)
        assertEquals(0, actual.size)
        assertEquals(expected, actual)
    }
}