package stickearn.movie.stickearnmovieapps.view.movieHome.nowPlaying

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.view.PaginationStatus
import stickearn.movie.stickearnmovieapps.utils.SingleLiveEvent

class NowPlayingMoviesDataSourceFactory(
    private val movieRepository: MovieRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, MovieData>() {

    var nowPlayingMoviesDataSource: NowPlayingMoviesDataSource? = null
    val paginationStatus = SingleLiveEvent<PaginationStatus>()

    override fun create(): DataSource<Int, MovieData> {

        nowPlayingMoviesDataSource = NowPlayingMoviesDataSource(
            paginationStatus,
            movieRepository,
            scope
        )

        return nowPlayingMoviesDataSource as NowPlayingMoviesDataSource
    }
}
