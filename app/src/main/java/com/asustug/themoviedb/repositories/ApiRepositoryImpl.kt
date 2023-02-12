package com.asustug.themoviedb.repositories

import com.asustug.themoviedb.data.model.MovieResponse
import com.asustug.themoviedb.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : ApiRepository {

    override suspend fun getMoviesList(apikey: String): Response<MovieResponse> {
       return apiService.getMoviesList(apikey)
    }

}