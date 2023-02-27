package com.asustug.themoviedb.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

data class MovieResponse(

	@Json(name="page")
	val page: Int? = null,

	@Json(name="total_pages")
	val totalPages: Int? = null,

	@Json(name="results")
	val results: List<Movie?>? = null,

	@Json(name="total_results")
	val totalResults: Int? = null
)
@Parcelize
data class Movie(

	@Json(name="overview")
	val overview: String? = null,

	@Json(name="original_language")
	val originalLanguage: String? = null,

	@Json(name="original_title")
	val originalTitle: String? = null,

	@Json(name="video")
	val video: Boolean? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="genre_ids")
	val genreIds: List<Int?>? = null,

	@Json(name="poster_path")
	val posterPath: String? = null,

	@Json(name="backdrop_path")
	val backdropPath: String? = null,

	@Json(name="media_type")
	val mediaType: String? = null,

	@Json(name="release_date")
	val releaseDate: String? = null,

	@Json(name="popularity")
	val popularity: String? = null,

	@Json(name="vote_average")
	val voteAverage: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="adult")
	val adult: Boolean? = null,

	@Json(name="vote_count")
	val voteCount: Int? = null
) : Parcelable

