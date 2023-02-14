package com.asustug.themoviedb.repositories

import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.model.MovieResponse
import retrofit2.Response

interface ApiRepository {

    suspend fun getMoviesList(apikey : String, page : Int) : MovieResponse

}