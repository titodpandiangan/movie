package stickearn.movie.stickearnmovieapps.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie WHERE id == :id")
    suspend fun findMovieById(id: Int): List<MovieEntity>

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)
}