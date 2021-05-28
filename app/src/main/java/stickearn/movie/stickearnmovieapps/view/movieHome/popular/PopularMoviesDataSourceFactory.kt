package stickearn.movie.stickearnmovieapps.view.movieHome.popular

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.view.PaginationStatus
import stickearn.movie.stickearnmovieapps.utils.SingleLiveEvent

class PopularMoviesDataSourceFactory(
    private val movieRepository: MovieRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, MovieData>() {

    var popularMoviesDataSource: PopularMoviesDataSource? = null
    val paginationStatus = SingleLiveEvent<PaginationStatus>()

    override fun create(): DataSource<Int, MovieData> {

        popularMoviesDataSource = PopularMoviesDataSource(
            paginationStatus,
            movieRepository,
            scope
        )

        return popularMoviesDataSource as PopularMoviesDataSource
    }
}
