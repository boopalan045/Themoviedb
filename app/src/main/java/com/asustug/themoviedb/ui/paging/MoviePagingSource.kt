package com.asustug.themoviedb.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.repositories.ApiRepository
import com.asustug.themoviedb.utils.Utils
import javax.inject.Inject

class MoviePagingSource @Inject constructor(private val apiRepository: ApiRepository) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val response = apiRepository.getMoviesList(Utils.apikey, page)
        return try {
            LoadResult.Page(
                response.results as MutableList<Movie>,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }
}