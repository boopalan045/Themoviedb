package com.asustug.themoviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asustug.themoviedb.R
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.remote.ApiService
import com.asustug.themoviedb.databinding.ActivityMainBinding
import com.asustug.themoviedb.repositories.ApiRepositoryImpl
import com.asustug.themoviedb.ui.adapters.MovieListAdapter
import com.asustug.themoviedb.ui.adapters.MoviePagingAdapter
import com.asustug.themoviedb.utils.Utils
import com.asustug.themoviedb.utils.Utils.Companion.apikey
import com.nec.devicemanagement.utils.Status
import com.nec.devicemanagement.utils.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var apiService: ApiService

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var utils: Utils

    lateinit var adapter: MovieListAdapter

    @Inject
    lateinit var moviePagingAdapter: MoviePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        apiCallViaFlow()
    }

    fun apiCallViaLiveData(){
        setupObserver()
        setupUI()
    }

    fun apiCallViaFlow(){
        initSetup()
    }

    fun initSetup() {
        setupViewModel()
        setupPagingUI()
        lifecycleScope.launchWhenStarted {
            mainViewModel.getAllMovies().collectLatest { response ->
                binding.apply {
                    rvLoadMovies.isVisible = true
                }
                moviePagingAdapter.submitData(response)
            }
        }
    }

    private fun setupUI() {
        val recyclerView = binding.rvLoadMovies
        adapter = MovieListAdapter(applicationContext,arrayListOf())
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }

    private fun setupPagingUI(){
        val recyclerView = binding.rvLoadMovies
        moviePagingAdapter = MoviePagingAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = moviePagingAdapter
    }

    private fun renderList(users: List<Movie>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiRepositoryImpl(apiService))
        )[MainViewModel::class.java]
    }

    private fun setupObserver() {
        mainViewModel.user.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    //processData(it)
                    utils.showSnackBar("data retrieved successfully!!!", binding.root)
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    utils.showSnackBar("something went wrong!!!", binding.root)
                }
            }
        }
    }

    private fun processData(results: List<Movie>) {
        results.let { movie -> renderList(movie) }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}