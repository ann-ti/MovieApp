package com.annti.movieapp.di

import com.annti.movieapp.data.network.MovieApi
import com.annti.movieapp.data.network.RetrofitFactory
import com.annti.movieapp.data.repository.FavoriteMovieRepository
import com.annti.movieapp.data.repository.FavoriteMovieRepositoryImpl
import com.annti.movieapp.data.repository.MovieRepository
import com.annti.movieapp.data.repository.MovieRepositoryImpl
import com.annti.movieapp.domain.FavoriteMovieUseCase
import com.annti.movieapp.domain.FavoriteMovieUseCaseImpl
import com.annti.movieapp.domain.MovieUseCase
import com.annti.movieapp.domain.MovieUseCaseImpl
import com.annti.movieapp.presentation.favorite.FavoriteMovieViewModel
import com.annti.movieapp.presentation.movie.MovieViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit
import org.koin.androidx.viewmodel.dsl.viewModel

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "6ccd72a2a8fc239b13f209408fc31c33"

val appModule = module {

    single {
        OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        RetrofitFactory(okHttpClient = get())
    }

    single {
        get<RetrofitFactory>().makeService<MovieApi>(BASE_URL)
    }

    single<MovieRepository> {
        MovieRepositoryImpl(
            movieApi = get()
        )
    }

    single<FavoriteMovieRepository> {
        FavoriteMovieRepositoryImpl()
    }

    single<MovieUseCase> {
        MovieUseCaseImpl(
            movieRepository = get(),
            apiKey = API_KEY
        )
    }

    single<FavoriteMovieUseCase> {
        FavoriteMovieUseCaseImpl(
            favoriteMovieRepository = get()
        )
    }

    viewModel {
        FavoriteMovieViewModel(
            favMovieUseCase = get()
        )
    }

    viewModel {
        MovieViewModel(
            movieUseCase = get()
        )
    }

}