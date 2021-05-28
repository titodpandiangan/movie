package stickearn.movie.stickearnmovieapps.di

import org.koin.dsl.module
import stickearn.movie.stickearnmovieapps.network.client.StickEarnMovieClient

val networkModule = module {

    single {
        StickEarnMovieClient().getMovieDbServices()
    }
}