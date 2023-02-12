package com.asustug.themoviedb.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asustug.themoviedb.data.model.MovieResponse
import com.asustug.themoviedb.repositories.ApiRepository
import com.nec.devicemanagement.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository) :  ViewModel() {

    val user = MutableLiveData<Resource<MovieResponse>>()

    fun fetchUsers(apikey : String) {
        viewModelScope.launch {
            user.postValue(Resource.loading(data = null))
            try {
                val response = repository.getMoviesList(apikey)
                if (response.code() == 200) {
                    user.value = Resource.success(response.body())
                } else {
                    user.value = Resource.error(response.message().toString(), response.body())
                }
            } catch (e: Exception) {
                user.postValue(Resource.error(e.localizedMessage!!.toString(), null))
            }
        }
    }
}