package stickearn.movie.stickearnmovieapps.view

sealed class PaginationStatus {

    object Empty : PaginationStatus()
    object Error : PaginationStatus()
}

