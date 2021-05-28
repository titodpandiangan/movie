package stickearn.movie.stickearnmovieapps.view.movieHome.popular

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_popular_movies.view.*
import stickearn.movie.stickearnmovieapps.BuildConfig
import stickearn.movie.stickearnmovieapps.R
import stickearn.movie.stickearnmovieapps.data.MovieData

class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun renderView(movieData: MovieData) {

        Picasso
            .get()
            .load(
                String.format(
                    "%s/t/p/w500/%s",
                    BuildConfig.BASE_TMDB_IMAGE_URL,
                    movieData.backdropPath
                )
            )
            .placeholder(R.drawable.ic_baseline_image_24)
            .fit()
            .into(itemView.ivPopularMovies)
    }
}