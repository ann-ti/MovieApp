package com.annti.movieapp.domain

import com.annti.movieapp.data.repository.MovieRepository
import com.annti.movieapp.data.model.Movie
import com.annti.movieapp.data.model.MovieDetails
import com.annti.movieapp.data.model.Results

interface MovieUseCase {
    suspend fun discoverMovie(): Movie
    suspend fun getMovieList(): List<Results>
    suspend fun getMovie(movieId: Int): MovieDetails
    suspend fun searchMovie(query: String): Movie
}

class MovieUseCaseImpl(
    private val apiKey: String,
    private val movieRepository: MovieRepository
) : MovieUseCase {
    override suspend fun discoverMovie(): Movie = movieRepository.discoverMovie(apiKey)

    override suspend fun getMovieList(): List<Results> =
        movieRepository.discoverMovie(apiKey).results

    override suspend fun getMovie(movieId: Int): MovieDetails =
        movieRepository.getMovie(movieId, apiKey)

    override suspend fun searchMovie(query: String): Movie =
        movieRepository.searchMovie(apiKey, query)
}