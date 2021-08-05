package stickearn.movie.stickearnmovieapps.view.movieFavorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_favorite_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import stickearn.movie.stickearnmovieapps.R
import stickearn.movie.stickearnmovieapps.database.MovieEntity


class FavoriteMovieFragment : Fragment() {

    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModel()

    private var movieFavoriteAdapter = FavoriteMovieAdapter()

    private var isFirstTimeLoad = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initEventListener()
    }

    private fun initView() {

        rvFavoriteMovie.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        rvFavoriteMovie.adapter = movieFavoriteAdapter
    }

    private fun initEventListener() {

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_favoriteMovieFragment_to_homeMovieFragment)
        }
    }

    private fun initObserver() {

        favoriteMovieViewModel.getFavoriteMoviesData().observe(viewLifecycleOwner, {
            renderFavoriteMovieData(it)
        })
    }

    private fun renderFavoriteMovieData(favoriteMovieData: PagedList<MovieEntity>) {

        if (pbFavoriteMovies.isVisible) pbFavoriteMovies.isVisible = false

        if (isFirstTimeLoad && favoriteMovieData.size == 0) {
            tvNoFavoriteMovies.isVisible = true
            isFirstTimeLoad = false
        }

        movieFavoriteAdapter.submitList(favoriteMovieData)
    }

}