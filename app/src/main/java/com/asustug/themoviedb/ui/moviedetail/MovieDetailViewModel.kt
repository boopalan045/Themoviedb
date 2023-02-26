package com.asustug.themoviedb.ui.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.model.MovieResponse
import com.asustug.themoviedb.repositories.ApiRepository
import com.asustug.themoviedb.ui.paging.MoviePagingSource
import com.nec.devicemanagement.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

}