package stickearn.movie.stickearnmovieapps.view.movieFavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import stickearn.movie.stickearnmovieapps.database.MovieEntity
import stickearn.movie.stickearnmovieapps.repository.MovieRepository

class FavoriteMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private fun initializePageConfig(): PagedList.Config {

        return PagedList.Config.Builder()
            .setPageSize(5)
            .setPrefetchDistance(30)
            .setInitialLoadSizeHint(15)
            .build()
    }

    fun getFavoriteMoviesData(): LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(
            movieRepository.getFavoriteMovies(),
            initializePageConfig()
        ).build()
    }
}