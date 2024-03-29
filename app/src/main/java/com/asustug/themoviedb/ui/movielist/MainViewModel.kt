package com.asustug.themoviedb.ui.movielist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.model.MovieResponse
import com.asustug.themoviedb.repositories.ApiRepository
import com.asustug.themoviedb.ui.paging.MoviePagingSearchSource
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
                val response = repository.getMoviesList("day", apikey, 1)
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
            config = PagingConfig(20,
                enablePlaceholders = false)
        ) {
            MoviePagingSource(repository)
        }.flow.cachedIn(viewModelScope)
    }

    fun getSearchResultsPaging(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20,
                enablePlaceholders = false)
        ) {
            MoviePagingSearchSource(repository,query)
        }.flow.cachedIn(viewModelScope)
    }

    /*val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }*/

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }
}