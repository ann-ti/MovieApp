package com.annti.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.annti.movieapp.data.model.Results

@Database(
    entities = [
        Results::class
    ], version = MoviesDatabase.DB_VERSION
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun favMovieDao(): MoviesDao

    companion object {
        const val DB_VERSION = 1
        const val DB_MAME = "movie_database"
    }
}