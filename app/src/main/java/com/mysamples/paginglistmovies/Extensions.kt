package com.mysamples.paginglistmovies

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders


inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(crossinline provider: () -> VM) =
    lazy {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                provider() as T
        }.let {
            ViewModelProviders.of(this, it).get(VM::class.java)
        }
    }
