package com.mysamples.paginglistmovies.feature.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mysamples.paginglistmovies.R
import com.mysamples.paginglistmovies.feature.TvShowModule
import com.mysamples.paginglistmovies.feature.model.DataState
import com.mysamples.paginglistmovies.viewModelProvider
import kotlinx.android.synthetic.main.activity_tvshow_list.*

class TvShowListActivity : AppCompatActivity() {

    private val viewModel by viewModelProvider { TvShowModule.viewModel() }

    private val adapter = TvShowAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_list)
        setSupportActionBar(toolbar)

        initViews()
        observeViewModel()
    }

    private fun initViews() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }


    private fun observeViewModel() {
        viewModel.tvShowLiveData().observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.dataLoadingState().observe(this, Observer {
            when (it) {
                DataState.ERROR -> {
                    showRetrySnackBar()
                    setDataState(it)
                }
                DataState.PAGE_LOADING, DataState.SUCCESS -> setDataState(it)
                else -> initial_progress.isVisible = true
            }
        })

    }

    private fun showRetrySnackBar() {
        Snackbar.make(root, "Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("RETRY") { viewModel.retry() }
            .show()
    }

    private fun setDataState(dataState: DataState) {
        initial_progress.isVisible = false
        adapter.setDataState(dataState)
    }

}
