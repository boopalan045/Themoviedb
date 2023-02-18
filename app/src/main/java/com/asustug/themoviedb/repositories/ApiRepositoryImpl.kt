package com.asustug.themoviedb.repositories

import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.model.MovieResponse
import com.asustug.themoviedb.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : ApiRepository {

    override suspend fun getMoviesList(classify: String, apikey: String, page : Int): MovieResponse {
       return apiService.getMoviesList(classify, apikey, page)
    }

}