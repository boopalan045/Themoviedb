package com.asustug.themoviedb.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.repositories.ApiRepository
import com.asustug.themoviedb.ui.movielist.MainActivity
import com.asustug.themoviedb.utils.Utils
import javax.inject.Inject

class MoviePagingSearchSource @Inject constructor(
    private val apiRepository: ApiRepository,
    private val queryString: String
) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val response = apiRepository.searchMovies(query = queryString,Utils.apikey, page)
        return try {
            LoadResult.Page(
                response.results as MutableList<Movie>,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if ((response.totalPages ?: 0) > page) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}