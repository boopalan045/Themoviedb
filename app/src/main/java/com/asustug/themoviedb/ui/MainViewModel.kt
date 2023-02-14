package com.asustug.themoviedb.ui

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
class MainViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    val user = MutableLiveData<Resource<MovieResponse>>()

    fun fetchUsers(apikey: String) {
        viewModelScope.launch {
            user.postValue(Resource.loading(data = null))
            try {
                val response = repository.getMoviesList(apikey, 1)
                /*if (response.code() == 200) {
                    user.value = Resource.success(response.body())
                } else {
                    user.value = Resource.error(response.message().toString(), response.body())
                }*/
            } catch (e: Exception) {
                user.postValue(Resource.error(e.localizedMessage!!.toString(), null))
            }
        }
    }

    fun getAllMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(25,
                enablePlaceholders = false)
        ) {
            MoviePagingSource(repository)
        }.flow.cachedIn(viewModelScope)
    }
}