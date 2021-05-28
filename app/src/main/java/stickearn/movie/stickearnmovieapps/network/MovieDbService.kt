package stickearn.movie.stickearnmovieapps.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import stickearn.movie.stickearnmovieapps.BuildConfig

interface MovieDbService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: String
    ): ListOfMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: String
    ): ListOfMoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: String
    ): ListOfMoviesResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: String,
        @Query("page") page: String
    ): MoviesReviewResponse

}