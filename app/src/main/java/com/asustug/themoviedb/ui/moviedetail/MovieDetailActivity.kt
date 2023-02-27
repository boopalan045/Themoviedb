package com.asustug.themoviedb.ui.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.asustug.themoviedb.R
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.remote.ApiService
import com.asustug.themoviedb.databinding.ActivityMainBinding
import com.asustug.themoviedb.databinding.ActivityMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    lateinit var binding: ActivityMovieDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        val item = intent.getParcelableExtra<Movie>("extra_item")
    }
}