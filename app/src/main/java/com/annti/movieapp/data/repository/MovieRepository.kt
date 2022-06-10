package com.annti.movieapp.data.repository

import com.annti.movieapp.data.network.MovieApi
import com.annti.movieapp.data.network.handleNetworkErrors
import com.annti.movieapp.data.model.Movie
import com.annti.movieapp.data.model.MovieDetails
import com.annti.movieapp.data.model.Results

interface MovieRepository {
    suspend fun discoverMovie(apiKey: String): Movie
    suspend fun getMovieList(apiKey: String): List<Results>
    suspend fun getMovie(movieId: Int, apiKey: String): MovieDetails
    suspend fun searchMovie(apiKey: String, query: String): Movie
}

class MovieRepositoryImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun discoverMovie(apiKey: String): Movie =
        handleNetworkErrors { movieApi.discoverMovie(apiKey) }

    override suspend fun getMovieList(apiKey: String): List<Results> = handleNetworkErrors {
        movieApi.discoverMovie(apiKey).results
    }

    override suspend fun getMovie(movieId: Int, apiKey: String): MovieDetails =
        handleNetworkErrors {
            movieApi.getMovie(movieId, apiKey)
        }

    override suspend fun searchMovie(apiKey: String, query: String): Movie = handleNetworkErrors {
        movieApi.searchMovie(apiKey, query)
    }

}