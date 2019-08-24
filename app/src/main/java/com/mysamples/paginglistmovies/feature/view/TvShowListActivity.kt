package com.mysamples.paginglistmovies.feature.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mysamples.paginglistmovies.R
import kotlinx.android.synthetic.main.activity_tvshow_list.*

class TvShowListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_list)
        setSupportActionBar(toolbar)

    }

}
