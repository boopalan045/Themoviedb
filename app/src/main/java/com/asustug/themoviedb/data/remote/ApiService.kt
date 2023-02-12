package com.asustug.themoviedb.data.remote

import com.asustug.themoviedb.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("trending/movie/day")
    suspend fun getMoviesList(@Query("api_key") apikey : String) : Response<MovieResponse>

}