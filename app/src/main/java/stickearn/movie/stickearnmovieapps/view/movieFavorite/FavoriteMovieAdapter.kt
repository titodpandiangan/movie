package stickearn.movie.stickearnmovieapps.view.movieFavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import stickearn.movie.stickearnmovieapps.R
import stickearn.movie.stickearnmovieapps.database.MovieEntity

class FavoriteMovieAdapter : PagedListAdapter<MovieEntity, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_favorite_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        getItem(position)?.let { (holder as FavoriteMovieViewHolder).renderView(it) }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.description == newItem.description
            }
        }
    }
}