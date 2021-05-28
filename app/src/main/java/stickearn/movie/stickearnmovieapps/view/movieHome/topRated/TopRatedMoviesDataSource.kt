package stickearn.movie.stickearnmovieapps.view.movieHome.topRated

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.view.PaginationStatus
import stickearn.movie.stickearnmovieapps.utils.SingleLiveEvent

class TopRatedMoviesDataSource(
    private val paginationStatus: SingleLiveEvent<PaginationStatus>,
    private val movieRepository: MovieRepository,
    private val scope: CoroutineScope

) : PageKeyedDataSource<Int, MovieData>() {

    private suspend fun getTopRatedMovieData(page: String) = movieRepository.getTopRatedMovies(page)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieData>
    ) {

        scope.launch(Dispatchers.IO) {

            try {

                val response = getTopRatedMovieData("1")

                withContext(Dispatchers.Main) {

                    if (response.listOfMovies.size == 0) {
                        paginationStatus.postValue(PaginationStatus.Empty)
                    } else {
                        callback.onResult(response.listOfMovies, null, 2)
                    }

                }

            } catch (e: Exception) {
                paginationStatus.postValue(PaginationStatus.Error)
                e.printStackTrace()
                return@launch
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieData>) {

        scope.launch(Dispatchers.IO) {

            try {

                val response = getTopRatedMovieData(params.key.toString())

                withContext(Dispatchers.Main) {
                    callback.onResult(response.listOfMovies, params.key + 1)
                }

            } catch (e: Exception) {
                paginationStatus.postValue(PaginationStatus.Error)
                e.printStackTrace()
                return@launch
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieData>) {
    }
}