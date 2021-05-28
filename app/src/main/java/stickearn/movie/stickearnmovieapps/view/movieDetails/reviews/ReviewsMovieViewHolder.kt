package stickearn.movie.stickearnmovieapps.view.movieDetails.reviews

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_review_movie.view.*
import stickearn.movie.stickearnmovieapps.data.MovieReviewData

class ReviewsMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun renderView(movieReviewData: MovieReviewData) {

        itemView.tvReviewAuthor.text = movieReviewData.author

        itemView.tvReviewContent.text = movieReviewData.content
    }
}