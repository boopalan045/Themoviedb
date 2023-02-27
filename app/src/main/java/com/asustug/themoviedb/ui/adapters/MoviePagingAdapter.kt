package com.asustug.themoviedb.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.asustug.themoviedb.R
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.databinding.LayoutMovieItemBinding
import com.asustug.themoviedb.ui.moviedetail.MovieDetailActivity
import com.asustug.themoviedb.utils.Utils.Companion.IMAGE_URL
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MoviePagingAdapter @Inject constructor(@ApplicationContext val applicationContext: Context) : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(Diff()) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie,applicationContext)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class MovieViewHolder(private val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, context: Context) {
            binding.apply {
                imgTitle.load(IMAGE_URL + movie.posterPath) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(R.drawable.ic_launcher_foreground)
                }
                tvTitle.text = movie.title
                imgTitle.setOnClickListener {
                    Snackbar.make(binding.root, movie.title.toString(), Snackbar.LENGTH_LONG).show()
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("extra_item", movie)
                    context.startActivity(intent)
                }
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }
}

