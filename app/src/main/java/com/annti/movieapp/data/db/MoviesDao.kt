package com.annti.movieapp.data.db

import androidx.room.*
import com.annti.movieapp.data.model.Results
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getFavorites(): Flow<List<Results>>

    @Query("SELECT * FROM movies WHERE id=:movieId")
    fun getMovie(movieId: Int): Results?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movie: Results)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllMovies(movies: List<Results>)

    @Delete
    fun removeMovie(movie: Results)

    @Query("DELETE FROM movies")
    fun clear()
}