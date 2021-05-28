package stickearn.movie.stickearnmovieapps.view.movieHome

import stickearn.movie.stickearnmovieapps.data.MovieData

fun interface OnMovieListener {
    fun onMovieClicked(movieData: MovieData)
}