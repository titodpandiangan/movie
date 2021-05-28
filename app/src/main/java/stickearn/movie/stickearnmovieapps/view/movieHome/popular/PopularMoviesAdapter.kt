package stickearn.movie.stickearnmovieapps.view.movieHome.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_popular_movies.view.*
import stickearn.movie.stickearnmovieapps.R
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.view.movieHome.OnMovieListener

class PopularMoviesAdapter : PagedListAdapter<MovieData, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {

    var onMovieListener: OnMovieListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PopularMoviesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_popular_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        getItem(position)?.let { (holder as PopularMoviesViewHolder).renderView(movieData = it) }

        (holder as PopularMoviesViewHolder).itemView.ivPopularMovies.setOnClickListener {
            getItem(position)?.let { it1 -> onMovieListener?.onMovieClicked(it1) }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieData>() {
            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.popularity == newItem.popularity
            }
        }
    }
}