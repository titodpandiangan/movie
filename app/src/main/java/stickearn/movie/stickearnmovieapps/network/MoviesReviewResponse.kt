package stickearn.movie.stickearnmovieapps.network

import com.google.gson.annotations.SerializedName
import stickearn.movie.stickearnmovieapps.data.MovieReviewData

class MoviesReviewResponse {

    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_pages")
    var totalPages: Int = 0

    @SerializedName("total_results")
    var totalResults: Int = 0

    @SerializedName("results")
    var listOfMoviesReviews: ArrayList<MovieReviewData> = arrayListOf()
}