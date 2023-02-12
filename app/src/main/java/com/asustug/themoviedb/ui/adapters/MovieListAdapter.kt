package com.asustug.themoviedb.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.asustug.themoviedb.R
import com.asustug.themoviedb.data.model.Movie
import com.asustug.themoviedb.utils.Utils.Companion.IMAGE_URL

class MovieListAdapter(private val context: Context, private val movieList: MutableList<Movie>) :
    RecyclerView.Adapter<MovieListAdapter.DataViewHolder>() {

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtView: TextView = itemView.findViewById(R.id.tv_title)
        private val imgMovie: ImageView = itemView.findViewById(R.id.img_title)

        fun bind(context: Context, position: Int, movie: MutableList<Movie>) {
            txtView.text = movie[position].title
            itemView.setOnClickListener {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            }
            imgMovie.load(IMAGE_URL+"${movie[position].posterPath}"){
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }
        }
    }

    fun addData(users: List<Movie>) {
        movieList.addAll(users)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_movie_item, viewGroup, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(context, position,movieList)
    }

    override fun getItemCount() = movieList.size

    /*fun setUserList(updatedUserList: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MovieDiffUtilCallback(movieList, updatedUserList))
        movieList.clear()
        movieList.addAll(updatedUserList)
        diffResult.dispatchUpdatesTo(this)
    }*/
}