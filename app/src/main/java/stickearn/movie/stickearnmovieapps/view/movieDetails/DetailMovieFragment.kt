package stickearn.movie.stickearnmovieapps.view.movieDetails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
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

class DetailMovieFragment : Fragment() {
    private val detailMovieViewModel: DetailMovieViewModel by viewModel {
        parametersOf(arguments?.getParcelable("data")!!)
    }

    private var reviewsMovieAdapter = ReviewsMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_detail_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initEventListener()
    }

    private fun initView() {

        rvMovieReviews.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        rvMovieReviews.adapter = reviewsMovieAdapter
    }

    private fun initObserver() {

        detailMovieViewModel.showMovieData.observe(viewLifecycleOwner, {
            renderMovieData(it)
        })

        detailMovieViewModel.initializeReviewsMovieLiveData().observe(viewLifecycleOwner, {
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
            findNavController().navigate(R.id.action_detailMovieFragment_to_homeMovieFragment)
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
            context?.let {
                ContextCompat.getDrawable(
                    it,
                    drawableId
                )
            }
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

}