package stickearn.movie.stickearnmovieapps.view.movieDetails.reviews

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stickearn.movie.stickearnmovieapps.data.MovieReviewData
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.view.PaginationStatus
import stickearn.movie.stickearnmovieapps.utils.SingleLiveEvent

class ReviewsMovieDataSource(
    private val id: String,
    private val paginationStatus: SingleLiveEvent<PaginationStatus>,
    private val movieRepository: MovieRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, MovieReviewData>() {

    private suspend fun getMovieReviewsData(movieId: String) =
        movieRepository.getMovieReviews(movieId = id, movieId)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieReviewData>
    ) {

        scope.launch(Dispatchers.IO) {

            try {

                val response = getMovieReviewsData("1")

                withContext(Dispatchers.Main) {

                    if (response.listOfMoviesReviews.size == 0) {
                        paginationStatus.postValue(PaginationStatus.Empty)
                    } else {
                        callback.onResult(response.listOfMoviesReviews, null, 2)
                    }
                }

            } catch (e: Exception) {
                paginationStatus.postValue(PaginationStatus.Error)
                e.printStackTrace()
                return@launch
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieReviewData>) {

        scope.launch(Dispatchers.IO) {

            try {

                val response = getMovieReviewsData(params.key.toString())

                withContext(Dispatchers.Main) {
                    callback.onResult(response.listOfMoviesReviews, params.key + 1)
                }

            } catch (e: Exception) {
                paginationStatus.postValue(PaginationStatus.Error)
                e.printStackTrace()
                return@launch
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieReviewData>) {
    }
}