package com.asustug.themoviedb.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asustug.themoviedb.databinding.LoadStateFooterBinding

class MovieListLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieListLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: LoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.button.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(state: LoadState) {
            binding.apply {
                progressBar.isVisible = state is LoadState.Loading
                button.isVisible = state !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            LoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

}