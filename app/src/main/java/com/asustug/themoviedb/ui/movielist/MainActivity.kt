package com.asustug.themoviedb.ui.movielist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.asustug.themoviedb.BuildConfig
import com.asustug.themoviedb.R
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.data.remote.ApiService
import com.asustug.themoviedb.databinding.ActivityMainBinding
import com.asustug.themoviedb.repositories.ApiRepositoryImpl
import com.asustug.themoviedb.ui.adapters.MovieListAdapter
import com.asustug.themoviedb.ui.adapters.MoviePagingAdapter
import com.asustug.themoviedb.ui.bottom_sheet.FilterBottomSheet
import com.asustug.themoviedb.ui.paging.MovieListLoadStateAdapter
import com.asustug.themoviedb.utils.NetworkHandler
import com.asustug.themoviedb.utils.Utils
import com.asustug.themoviedb.utils.ViewModelFactory
import com.asustug.themoviedb.utils.dataStore.PreferenceDataStoreHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.nec.devicemanagement.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.Forest.plant
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

    @Inject
    lateinit var networkHandler: NetworkHandler

    @Inject
    lateinit var filterBottomSheet: FilterBottomSheet

    @Inject
    lateinit var preferenceDataStoreHelper: PreferenceDataStoreHelper

    private val TAG = MainActivity::class.java.simpleName

    lateinit var flow: Flow<String>

    init {
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
    }

    companion object CLASSIFY {
        var finalData = "week"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        saveTolocal()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        binding.fabFilter.setOnClickListener {
            customBottomSheet()
        }
    }

    private fun customBottomSheet() {
        val bottomSheet = findViewById<ConstraintLayout>(R.id.bottomSheet)
        val chipDay = findViewById<Chip>(R.id.chip_day)
        val chipWeek = findViewById<Chip>(R.id.chip_week)
        val filterSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        filterSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        chipDay.setOnClickListener {
            if (chipDay.isChecked()) {
                Toast.makeText(applicationContext, "Day", Toast.LENGTH_SHORT).show()
                finalData = "day"
                hideBottomSheet(filterSheetBehavior)
            }
        }
        chipWeek.setOnClickListener {
            if (chipWeek.isChecked()) {
                Toast.makeText(applicationContext, "Week", Toast.LENGTH_SHORT).show()
                finalData = "week"
                hideBottomSheet(filterSheetBehavior)
            }
        }
    }

    fun hideBottomSheet(filterSheetBehavior: BottomSheetBehavior<ConstraintLayout>) {
        apiCallViaFlow()
        filterSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        filterSheetBehavior.isHideable = true
    }

    override fun onResume() {
        super.onResume()
        if (networkHandler.isNetworkAvailable()) {
            apiCallViaFlow()
        } else {
            Timber.d("Network is not available")
            Snackbar.make(binding.root, "Network is not available", Snackbar.LENGTH_LONG).show()
        }
    }

    fun apiCallViaLiveData() {
        setupObserver()
        setupUI()
    }

    private fun apiCallViaFlow() {
        initSetup()
    }

    private fun initSetup() {
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
        adapter = MovieListAdapter(applicationContext, arrayListOf())
        recyclerView.layoutManager.apply {
            GridLayoutManager(applicationContext, 3)
        }
        recyclerView.adapter = adapter
    }

    private fun setupPagingUI() {
        val recyclerView = binding.rvLoadMovies
        moviePagingAdapter = MoviePagingAdapter()
        val footerAdapter = MovieListLoadStateAdapter{}
        val concatAdapter = moviePagingAdapter.withLoadStateFooter(
            footer = footerAdapter
        )
        recyclerView.adapter = concatAdapter
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if (position == concatAdapter.itemCount - 1 && footerAdapter.itemCount > 0){
                    2
                } else {
                    1
                }
            }
        }
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
                    Timber.e("please wait...")
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

    fun saveTolocal() = lifecycleScope.launch {
        preferenceDataStoreHelper.putPreference(stringPreferencesKey("id"), "week")
    }
}