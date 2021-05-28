package stickearn.movie.stickearnmovieapps.view.movieDetails.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import stickearn.movie.stickearnmovieapps.R
import stickearn.movie.stickearnmovieapps.data.MovieReviewData

class ReviewsMovieAdapter : PagedListAdapter<MovieReviewData, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewsMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_review_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        getItem(position)?.let { (holder as ReviewsMovieViewHolder).renderView(it) }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieReviewData>() {
            override fun areItemsTheSame(
                oldItem: MovieReviewData,
                newItem: MovieReviewData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieReviewData,
                newItem: MovieReviewData
            ): Boolean {
                return oldItem.content == newItem.content
            }
        }
    }
}