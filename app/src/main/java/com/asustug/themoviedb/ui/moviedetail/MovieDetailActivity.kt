package com.asustug.themoviedb.ui.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import coil.load
import coil.size.Scale
import com.asustug.themoviedb.R
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.remote.ApiService
import com.asustug.themoviedb.databinding.ActivityMainBinding
import com.asustug.themoviedb.databinding.ActivityMovieDetailBinding
import com.asustug.themoviedb.utils.Utils
import com.google.android.material.snackbar.Snackbar
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
        val movie = intent.getParcelableExtra<Movie>("extra_item")
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.imgPoster.apply {
            load(Utils.IMAGE_URL + movie!!.posterPath) {
                crossfade(true)
                crossfade(1000)
                scale(Scale.FIT)
                placeholder(R.drawable.ic_launcher_foreground)
            }
        }
        Snackbar.make(binding.root, movie!!.title!!,Snackbar.LENGTH_SHORT).show()
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}