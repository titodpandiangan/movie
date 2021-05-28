package stickearn.movie.stickearnmovieapps.di

import androidx.room.Room
import org.koin.dsl.module
import stickearn.movie.stickearnmovieapps.database.MovieDatabase

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java,
            "MovieDatabase"
        ).build()
    }
}