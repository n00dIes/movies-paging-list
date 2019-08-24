package com.mysamples.paginglistmovies.feature.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mysamples.paginglistmovies.R
import com.mysamples.paginglistmovies.feature.TvShowModule
import com.mysamples.paginglistmovies.feature.model.TvShow
import com.mysamples.paginglistmovies.viewModelProvider
import kotlinx.android.synthetic.main.activity_tvshow_list.*

class TvShowListActivity : AppCompatActivity() {

    private val viewModel by viewModelProvider { TvShowModule.viewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_list)
        setSupportActionBar(toolbar)
    }

    private fun observeViewModel() {
        viewModel.tvShowLiveData().observe(this, Observer<PagedList<TvShow>> {
            // TODO adapter.submitList(it)
        })

    }
}
