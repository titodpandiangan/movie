package stickearn.movie.stickearnmovieapps.network

import com.google.gson.annotations.SerializedName
import stickearn.movie.stickearnmovieapps.data.MovieData

class ListOfMoviesResponse {

    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_pages")
    var totalPages: Int = 0

    @SerializedName("total_results")
    var totalResults: Int = 0

    @SerializedName("dates")
    var dates: Dates = Dates()

    @SerializedName("results")
    var listOfMovies: ArrayList<MovieData> = arrayListOf()

    class Dates {

        var maximum: String = ""
        var minimum: String = ""
    }
}