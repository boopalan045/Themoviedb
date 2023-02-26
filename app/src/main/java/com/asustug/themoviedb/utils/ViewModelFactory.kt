package com.asustug.themoviedb.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asustug.themoviedb.repositories.ApiRepository
import com.asustug.themoviedb.ui.movielist.MainViewModel

class ViewModelFactory(private val apiHelper: ApiRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
