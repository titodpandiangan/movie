package stickearn.movie.stickearnmovieapps.view.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.data.MovieReviewData
import stickearn.movie.stickearnmovieapps.database.MovieEntity
import stickearn.movie.stickearnmovieapps.repository.MovieRepository
import stickearn.movie.stickearnmovieapps.utils.SingleLiveEvent
import stickearn.movie.stickearnmovieapps.view.movieDetails.reviews.ReviewsMovieDataSourceFactory

class DetailMovieViewModel(
    private val movieData: MovieData,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val showMovieData = MutableLiveData<MovieData>()
    val saveFavoriteMovieEvent = SingleLiveEvent<Boolean>()
    val changeFavoriteIconColorEvent = SingleLiveEvent<Boolean>()
    val shareLinkEvent = SingleLiveEvent<String>()

    private var isFavorite = false

    var reviewsMovieDataSourceFactory: ReviewsMovieDataSourceFactory? = null

    init {
        getMovieData()
    }

    private fun initializePageConfig(): PagedList.Config {

        return PagedList.Config.Builder()
            .setPageSize(5)
            .setPrefetchDistance(30)
            .setInitialLoadSizeHint(15)
            .build()
    }

    fun initializeReviewsMovieLiveData(): LiveData<PagedList<MovieReviewData>> {

        reviewsMovieDataSourceFactory = ReviewsMovieDataSourceFactory(
            movieData.id.toString(),
            movieRepository,
            viewModelScope
        )

        return LivePagedListBuilder(
            reviewsMovieDataSourceFactory!!,
            initializePageConfig()
        ).build()
    }

    private fun getMovieData() {

        showMovieData.value = movieData

        viewModelScope.launch(Dispatchers.IO) {

            try {

                isFavorite = movieRepository.findMovieById(movieData.id).isNotEmpty()

                withContext(Dispatchers.Main) {
                    changeFavoriteIconColorEvent.value = isFavorite
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun favoriteIconClicked() {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                if (!isFavorite) {
                    isFavorite = true
                    movieRepository.insertMovies(mappingToEntity(movieData))
                } else {
                    isFavorite = false
                    movieRepository.deleteMovies(mappingToEntity(movieData))
                }

                withContext(Dispatchers.Main) {
                    saveFavoriteMovieEvent.value = isFavorite
                    changeFavoriteIconColorEvent.value = isFavorite
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun shareIconClicked() {
        shareLinkEvent.value = movieData.title
    }

    private fun mappingToEntity(movieData: MovieData): MovieEntity {

        return MovieEntity(
            movieData.id,
            movieData.title,
            movieData.releaseDate,
            movieData.overview,
            movieData.posterPath
        )
    }
}