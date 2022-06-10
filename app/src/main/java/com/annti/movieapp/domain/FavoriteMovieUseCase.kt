package com.annti.movieapp.domain

import com.annti.movieapp.data.model.Results
import com.annti.movieapp.data.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieUseCase {
    suspend fun saveMovie(movie: Results)
    fun getFavorites(): Flow<List<Results>>
    suspend fun removeMovie(movie: Results)
    suspend fun getMovieId(movieId: Int): Results?
    suspend fun removeAllMovies()
}

class FavoriteMovieUseCaseImpl(
    private val favoriteMovieRepository: FavoriteMovieRepository
) : FavoriteMovieUseCase {

    override suspend fun saveMovie(movie: Results) {
        favoriteMovieRepository.saveMovie(movie)
    }

    override fun getFavorites(): Flow<List<Results>> =
        favoriteMovieRepository.getFavorites()

    override suspend fun removeMovie(movie: Results) {
        favoriteMovieRepository.removeMovie(movie)
    }

    override suspend fun getMovieId(movieId: Int): Results? =
        favoriteMovieRepository.getMovie(movieId)

    override suspend fun removeAllMovies() {
        favoriteMovieRepository.removeAllMovies()
    }
}

