package stickearn.movie.stickearnmovieapps.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieData(

    @SerializedName("poster_path")
    var posterPath: String = "",

    @SerializedName("adult")
    var isAdult: Boolean = false,

    @SerializedName("overview")
    var overview: String = "",

    @SerializedName("release_date")
    var releaseDate: String = "",

    @SerializedName("genre_ids")
    var genreIds: ArrayList<Int> = arrayListOf(),

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("original_title")
    var originalTitle: String = "",

    @SerializedName("original_language")
    var originalLanguage: String = "",

    @SerializedName("title")
    var title: String = "",

    @SerializedName("backdrop_path")
    var backdropPath: String = "",

    @SerializedName("popularity")
    var popularity: Double = 0.0,

    @SerializedName("vote_count")
    var voteCount: Long = 0L,

    @SerializedName("video")
    var isVideo: Boolean = false,

    @SerializedName("vote_average")
    var voteAverage: Double = 0.0
) : Parcelable