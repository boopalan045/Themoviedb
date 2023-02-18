package com.asustug.themoviedb.data.remote

import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("trending/movie/{id}")
    suspend fun getMoviesList(
        @Path("id") id: String,
        @Query("api_key") apikey : String,
        @Query("page") page : Int
    ) : MovieResponse
}