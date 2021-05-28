package stickearn.movie.stickearnmovieapps.view.movieDetails.reviews

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import stickearn.movie.stickearnmovieapps.data.MovieReviewData
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.view.PaginationStatus
import stickearn.movie.stickearnmovieapps.utils.SingleLiveEvent

class ReviewsMovieDataSourceFactory(
    private val id: String,
    private val movieRepository: MovieRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, MovieReviewData>() {

    var reviewsMovieDataSource: ReviewsMovieDataSource? = null
    val paginationStatus = SingleLiveEvent<PaginationStatus>()

    override fun create(): DataSource<Int, MovieReviewData> {

        reviewsMovieDataSource = ReviewsMovieDataSource(
            id,
            paginationStatus,
            movieRepository,
            scope
        )

        return reviewsMovieDataSource as ReviewsMovieDataSource
    }
}
