package stickearn.movie.stickearnmovieapps.view.movieDetails

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import stickearn.movie.stickearnmovieapps.BuildConfig
import stickearn.movie.stickearnmovieapps.R
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.data.MovieReviewData
import stickearn.movie.stickearnmovieapps.view.PaginationStatus
import stickearn.movie.stickearnmovieapps.view.movieDetails.reviews.ReviewsMovieAdapter


class DetailMovieActivity : AppCompatActivity() {

    private val detailMovieViewModel: DetailMovieViewModel by viewModel {
        parametersOf(intent.getParcelableExtra(MOVIE_DATA))
    }

    private var reviewsMovieAdapter = ReviewsMovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        initView()
        initObserver()
        initEventListener()
    }

    private fun initView() {

        rvMovieReviews.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        rvMovieReviews.adapter = reviewsMovieAdapter
    }

    private fun initObserver() {

        detailMovieViewModel.showMovieData.observe(this, {
            renderMovieData(it)
        })

        detailMovieViewModel.initializeReviewsMovieLiveData().observe(this, {
            renderMovieReviews(it)
        })

        detailMovieViewModel.reviewsMovieDataSourceFactory?.paginationStatus?.observe(this, {
            checkMovieReviewsPaginationStatus(it)
        })

        detailMovieViewModel.changeFavoriteIconColorEvent.observe(this, {
            renderFavoriteIcon(it)
        })

        detailMovieViewModel.saveFavoriteMovieEvent.observe(this, {
            renderSaveFavoriteSnackbar(it)
        })

        detailMovieViewModel.shareLinkEvent.observe(this, {
            shareMovie(it)
        })
    }

    private fun initEventListener() {

        toolbar.setNavigationOnClickListener {
            finish()
        }

        ivFavoriteMovie.setOnClickListener {
            favoriteIconClicked()
        }

        ivShareMovie.setOnClickListener {
            shareIconClicked()
        }
    }

    private fun favoriteIconClicked() {
        detailMovieViewModel.favoriteIconClicked()
    }

    private fun shareIconClicked() {
        detailMovieViewModel.shareIconClicked()
    }

    private fun renderMovieData(movieData: MovieData) {

        cToolbarDetailMovie.title = movieData.title

        tvMovieReleaseDate.text = LocalDate.parse(
            movieData.releaseDate,
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        ).format(DateTimeFormatter.ofPattern("LLL dd,yyyy"))

        Picasso
            .get()
            .load(
                String.format(
                    "%s/t/p/w500/%s",
                    BuildConfig.BASE_TMDB_IMAGE_URL,
                    movieData.backdropPath
                )
            )
            .fit()
            .into(ivMovieToolbar)

        tvMovieName.text = movieData.title

        tvMovieDescription.text = movieData.overview

    }

    private fun renderMovieReviews(movieReviews: PagedList<MovieReviewData>) {
        reviewsMovieAdapter.submitList(movieReviews)
    }

    private fun changeFavoriteIcon(drawableId: Int) {
        ivFavoriteMovie.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                drawableId
            )
        )
    }

    private fun renderFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) changeFavoriteIcon(R.drawable.ic_twotone_favorite_24)
        else changeFavoriteIcon(R.drawable.ic_twotone_unfavorite_24)
    }

    private fun showSnackBar(message: String) {

        Snackbar
            .make(
                coordinator_layout,
                message,
                Snackbar.LENGTH_SHORT
            ).setAnchorView(clMenuDetailMovie)
            .show()
    }

    private fun renderSaveFavoriteSnackbar(isFavorite: Boolean) {
        if (isFavorite) {
            showSnackBar(getString(R.string.marked_favorite_movies))
        } else {
            showSnackBar(getString(R.string.unmarked_favorite_movies))
        }
    }

    private fun shareMovie(message: String) {
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, message)

        startActivity(Intent.createChooser(intent, "Share"))
    }

    private fun checkMovieReviewsPaginationStatus(paginationStatus: PaginationStatus) {
        when (paginationStatus) {
            is PaginationStatus.Empty -> tvNoReviewsMovie.isVisible = true
        }
    }

    companion object {
        const val MOVIE_DATA = "MovieData"
    }
}