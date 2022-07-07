package com.annti.movieapp.data.repository

import com.annti.movieapp.data.db.Database
import com.annti.movieapp.data.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface FavoriteMovieRepository {
    suspend fun saveMovie(movie: Results)
    fun getFavoriteMovies(): Flow<List<Results>>
    suspend fun removeMovie(movie: Results)
    suspend fun getMovie(movieId: Int): Results?
    suspend fun removeAllMovies()
}

class FavoriteMovieRepositoryImpl : FavoriteMovieRepository {

    private val favoriteMovieDao = Database.instance.favMovieDao()

    override suspend fun saveMovie(movie: Results) {
        withContext(Dispatchers.IO) {
            favoriteMovieDao.saveMovie(movie)
        }
    }

    override fun getFavoriteMovies(): Flow<List<Results>> =
        favoriteMovieDao.getFavorites()

    override suspend fun removeMovie(movie: Results) {
        withContext(Dispatchers.IO) { favoriteMovieDao.removeMovie(movie) }
    }

    override suspend fun getMovie(movieId: Int): Results? =
        withContext(Dispatchers.IO) { favoriteMovieDao.getMovie(movieId) }

    override suspend fun removeAllMovies() {
        withContext(Dispatchers.IO) { favoriteMovieDao.clear() }
    }
}