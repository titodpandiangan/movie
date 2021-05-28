package stickearn.movie.stickearnmovieapps.repository

import stickearn.movie.stickearnmovieapps.database.MovieDatabase
import stickearn.movie.stickearnmovieapps.database.MovieEntity
import stickearn.movie.stickearnmovieapps.network.MovieDbService

class MovieRepository(
    private val movieDbService: MovieDbService,
    private val movieDatabase: MovieDatabase
) {

    suspend fun getPopularMovies(page: String) = movieDbService.getPopularMovies(page)

    suspend fun getTopRatedMovies(page: String) = movieDbService.getTopRatedMovies(page)

    suspend fun getNowPlayingMovies(page: String) = movieDbService.getNowPlayingMovies(page)

    suspend fun getMovieReviews(movieId: String, page: String) =
        movieDbService.getMovieReviews(movieId, page)

    fun getFavoriteMovies() = movieDatabase.movieDao().getAllMovies()

    suspend fun insertMovies(movieEntity: MovieEntity) =
        movieDatabase.movieDao().insertMovie(movieEntity)

    suspend fun deleteMovies(movieEntity: MovieEntity) =
        movieDatabase.movieDao().deleteMovie(movieEntity)

    suspend fun findMovieById(id: Int) = movieDatabase.movieDao().findMovieById(id)
}