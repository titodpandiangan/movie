package stickearn.movie.stickearnmovieapps.view.movieHome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.utils.SingleLiveEvent
import stickearn.movie.stickearnmovieapps.view.movieHome.nowPlaying.NowPlayingMoviesDataSourceFactory
import stickearn.movie.stickearnmovieapps.view.movieHome.popular.PopularMoviesDataSourceFactory
import stickearn.movie.stickearnmovieapps.view.movieHome.topRated.TopRatedMoviesDataSourceFactory

class HomeMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val goToDetailMovieEvent = SingleLiveEvent<MovieData>()
    val goToFavoriteActivityEvent = SingleLiveEvent<Any>()

    var popularMoviesDataSourceFactory: PopularMoviesDataSourceFactory? = null
    var topRatedMoviesDataSourceFactory: TopRatedMoviesDataSourceFactory? = null
    var nowPlayingMoviesDataSourceFactory: NowPlayingMoviesDataSourceFactory? = null

    private fun initializePageConfig(): PagedList.Config {

        return PagedList.Config.Builder()
            .setPageSize(5)
            .setPrefetchDistance(30)
            .setInitialLoadSizeHint(15)
            .build()
    }

    fun initializePopularMoviesLiveData(): LiveData<PagedList<MovieData>> {

        popularMoviesDataSourceFactory = PopularMoviesDataSourceFactory(
            movieRepository,
            viewModelScope
        )

        return LivePagedListBuilder(
            popularMoviesDataSourceFactory!!,
            initializePageConfig()
        ).build()
    }

    fun initializeTopRatedMoviesLiveData(): LiveData<PagedList<MovieData>> {

        topRatedMoviesDataSourceFactory = TopRatedMoviesDataSourceFactory(
            movieRepository,
            viewModelScope
        )

        return LivePagedListBuilder(
            topRatedMoviesDataSourceFactory!!,
            initializePageConfig()
        ).build()
    }

    fun initializeNowPlayingMoviesLiveData(): LiveData<PagedList<MovieData>> {

        nowPlayingMoviesDataSourceFactory = NowPlayingMoviesDataSourceFactory(
            movieRepository,
            viewModelScope
        )

        return LivePagedListBuilder(
            nowPlayingMoviesDataSourceFactory!!,
            initializePageConfig()
        ).build()
    }

    fun movieClicked(movieData: MovieData) {
        goToDetailMovieEvent.postValue(movieData)
    }

    fun favoriteIconClicked() {
        goToFavoriteActivityEvent.call()
    }

    fun refreshPopularMovie() {
        popularMoviesDataSourceFactory?.popularMoviesDataSource?.invalidate()
    }

    fun refreshTopRatedMovie() {
        topRatedMoviesDataSourceFactory?.topRatedMoviesDataSource?.invalidate()
    }

    fun refreshNowPlayingMovie() {
        nowPlayingMoviesDataSourceFactory?.nowPlayingMoviesDataSource?.invalidate()
    }
}