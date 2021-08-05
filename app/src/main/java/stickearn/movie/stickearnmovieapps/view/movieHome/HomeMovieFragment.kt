package stickearn.movie.stickearnmovieapps.view.movieHome

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home_movie.*
import kotlinx.android.synthetic.main.layout_home_now_playing_movies.*
import kotlinx.android.synthetic.main.layout_home_popular_movies.*
import kotlinx.android.synthetic.main.layout_home_top_rated_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import stickearn.movie.stickearnmovieapps.R
import stickearn.movie.stickearnmovieapps.data.MovieData
import stickearn.movie.stickearnmovieapps.view.PaginationStatus
import stickearn.movie.stickearnmovieapps.view.movieHome.popular.PopularMoviesAdapter

class HomeMovieFragment : Fragment() {

    private val homeMovieViewModel: HomeMovieViewModel by viewModel()

    private var popularMoviesAdapter = PopularMoviesAdapter()
    private var topRatedMoviesAdapter = MoviesAdapter()
    private var nowPlayingMoviesAdapter = MoviesAdapter()

    private var recyclerViewDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            outRect.top = resources
                .getDimension(R.dimen.base16).toInt()
            outRect.bottom = resources
                .getDimension(R.dimen.base16).toInt()
            outRect.left = (if (position == 0) resources
                .getDimension(R.dimen.base16) else resources
                .getDimension(R.dimen.base8)).toInt()
            outRect.right = (if (position == state.itemCount - 1) resources
                .getDimension(R.dimen.base16) else resources
                .getDimension(R.dimen.base8)).toInt()
        }
    }

    private var movieListener = movieListener()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initEventListener()
    }

    private fun initView() {

        rvPopularMovies.adapter = popularMoviesAdapter
        rvTopRatedMovies.adapter = topRatedMoviesAdapter
        rvNowPlayingMovies.adapter = nowPlayingMoviesAdapter

        rvPopularMovies.addItemDecoration(recyclerViewDecoration)
        rvTopRatedMovies.addItemDecoration(recyclerViewDecoration)
        rvNowPlayingMovies.addItemDecoration(recyclerViewDecoration)
    }

    private fun initObserver() {

        homeMovieViewModel.initializePopularMoviesLiveData().observe(viewLifecycleOwner, {
            renderPopularMovieData(it)
        })

        homeMovieViewModel.initializeTopRatedMoviesLiveData().observe(viewLifecycleOwner, {
            renderTopRatedMovieData(it)
        })

        homeMovieViewModel.initializeNowPlayingMoviesLiveData().observe(viewLifecycleOwner, {
            renderNowPlayingMovieData(it)
        })

        homeMovieViewModel.popularMoviesDataSourceFactory?.paginationStatus?.observe(this, {
            checkPopularMoviesPaginationStatus(it)
        })

        homeMovieViewModel.topRatedMoviesDataSourceFactory?.paginationStatus?.observe(this, {
            checkTopRatedMoviesPaginationStatus(it)
        })

        homeMovieViewModel.nowPlayingMoviesDataSourceFactory?.paginationStatus?.observe(this, {
            checkNowPlayingMoviesPaginationStatus(it)
        })

        homeMovieViewModel.goToDetailMovieEvent.observe(this, {
            goToDetailMovieEvent(it)
        })

        homeMovieViewModel.goToFavoriteActivityEvent.observe(this, {
            goToFavoriteMovieEvent()
        })
    }

    private fun initEventListener() {

        popularMoviesAdapter.onMovieListener = movieListener
        topRatedMoviesAdapter.onMovieListener = movieListener
        nowPlayingMoviesAdapter.onMovieListener = movieListener

        toolbar.menu.findItem(R.id.menuFavoriteHome).setOnMenuItemClickListener {
            favoriteIconClicked()
            true
        }

        btnRefreshPopularMovies.setOnClickListener {
            refreshPopularMovie()
        }

        btnRefreshTopRatedMovies.setOnClickListener {
            refreshTopRatedMovie()
        }

        btnRefreshNowPlayingMovies.setOnClickListener {
            refreshNowPlayingMovie()
        }
    }

    private fun movieListener(): OnMovieListener {
        return OnMovieListener {
            moviesClicked(it)
        }
    }

    private fun favoriteIconClicked() {
        homeMovieViewModel.favoriteIconClicked()
    }

    private fun moviesClicked(movieData: MovieData) {
        homeMovieViewModel.movieClicked(movieData)
    }

    private fun goToDetailMovieEvent(movieData: MovieData) {

        val bundle = Bundle()
        bundle.putParcelable("data", movieData)
        findNavController().navigate(R.id.detailMovieFragment, bundle)
    }

    private fun goToFavoriteMovieEvent() {
        findNavController().navigate(R.id.action_homeMovieFragment_to_favoriteMovieFragment)
    }

    private fun refreshPopularMovie() {

        btnRefreshPopularMovies.isVisible = false
        pbPopularMovies.isVisible = true
        rvPopularMovies.isVisible = false

        homeMovieViewModel.refreshPopularMovie()
    }

    private fun refreshTopRatedMovie() {

        btnRefreshTopRatedMovies.isVisible = false
        pbTopRatedMovies.isVisible = true
        rvTopRatedMovies.isVisible = false

        homeMovieViewModel.refreshTopRatedMovie()
    }

    private fun refreshNowPlayingMovie() {

        btnRefreshNowPlayingMovies.isVisible = false
        pbNowPlayingMovies.isVisible = true
        rvNowPlayingMovies.isVisible = false

        homeMovieViewModel.refreshNowPlayingMovie()
    }

    private fun renderPopularMovieData(movieDatas: PagedList<MovieData>) {

        if (pbPopularMovies.isVisible) pbPopularMovies.isVisible = false
        if (!rvPopularMovies.isVisible) rvPopularMovies.isVisible = true

        popularMoviesAdapter.submitList(movieDatas)
    }

    private fun renderTopRatedMovieData(movieDatas: PagedList<MovieData>) {

        if (pbTopRatedMovies.isVisible) pbTopRatedMovies.isVisible = false
        if (!rvTopRatedMovies.isVisible) rvTopRatedMovies.isVisible = true

        topRatedMoviesAdapter.submitList(movieDatas)
    }

    private fun renderNowPlayingMovieData(movieDatas: PagedList<MovieData>) {

        if (pbNowPlayingMovies.isVisible) pbNowPlayingMovies.isVisible = false
        if (!rvNowPlayingMovies.isVisible) rvNowPlayingMovies.isVisible = true

        nowPlayingMoviesAdapter.submitList(movieDatas)
    }

    private fun checkPopularMoviesPaginationStatus(paginationStatus: PaginationStatus) {

        when (paginationStatus) {
            is PaginationStatus.Error -> {
                rvPopularMovies.isVisible = false
                pbPopularMovies.isVisible = false
                btnRefreshPopularMovies.isVisible = true
            }
        }
    }

    private fun checkTopRatedMoviesPaginationStatus(paginationStatus: PaginationStatus) {

        when (paginationStatus) {
            is PaginationStatus.Error -> {

                rvTopRatedMovies.isVisible = false
                pbTopRatedMovies.isVisible = false
                btnRefreshTopRatedMovies.isVisible = true
            }
        }
    }

    private fun checkNowPlayingMoviesPaginationStatus(paginationStatus: PaginationStatus) {

        when (paginationStatus) {
            is PaginationStatus.Error -> {

                rvNowPlayingMovies.isVisible = false
                pbNowPlayingMovies.isVisible = false
                btnRefreshNowPlayingMovies.isVisible = true
            }
        }
    }

}