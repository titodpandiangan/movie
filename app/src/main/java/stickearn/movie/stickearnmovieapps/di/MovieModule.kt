package stickearn.movie.stickearnmovieapps.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.network.MovieDbService
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.view.movieDetails.DetailMovieViewModel
import stickearn.movie.stickearnmovieapps.view.movieFavorite.FavoriteMovieViewModel
import stickearn.movie.stickearnmovieapps.view.movieHome.HomeMovieViewModel

val movieModule = module {

    factory {
        MovieRepository(
            get<Retrofit>().create(MovieDbService::class.java),
            get()
        )
    }

    viewModel { HomeMovieViewModel(get()) }
    viewModel { (movieData: MovieData) -> DetailMovieViewModel(movieData, get()) }
    viewModel { FavoriteMovieViewModel(get()) }
}