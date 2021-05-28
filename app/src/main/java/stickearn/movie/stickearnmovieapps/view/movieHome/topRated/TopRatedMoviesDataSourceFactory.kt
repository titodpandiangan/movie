package stickearn.movie.stickearnmovieapps.view.movieHome.topRated

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.view.PaginationStatus
import stickearn.movie.stickearnmovieapps.utils.SingleLiveEvent

class TopRatedMoviesDataSourceFactory(
    private val movieRepository: MovieRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, MovieData>() {

    var topRatedMoviesDataSource: TopRatedMoviesDataSource? = null
    val paginationStatus = SingleLiveEvent<PaginationStatus>()

    override fun create(): DataSource<Int, MovieData> {

        topRatedMoviesDataSource = TopRatedMoviesDataSource(
            paginationStatus,
            movieRepository,
            scope
        )

        return topRatedMoviesDataSource as TopRatedMoviesDataSource
    }
}
