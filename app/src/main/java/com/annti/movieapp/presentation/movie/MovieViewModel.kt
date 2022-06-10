package com.annti.movieapp.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.movieapp.domain.MovieUseCase
import com.annti.movieapp.data.model.Movie
import com.annti.movieapp.data.model.MovieDetails
import com.annti.movieapp.data.model.Results
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private var movieLiveData = MutableLiveData<Movie>()
    private var movieDetailsLiveData = MutableLiveData<MovieDetails>()
    private var movieListLiveData = MutableLiveData<List<Results>>()
    private var errorData = LiveEvent<String>()
    private var errorViewData = LiveEvent<Boolean>()
    private var loadingLiveData = MutableLiveData<Boolean>()

    val movie: LiveData<Movie>
        get() = movieLiveData
    val movieList: LiveData<List<Results>>
        get() = movieListLiveData
    val movieDetails: LiveData<MovieDetails>
        get() = movieDetailsLiveData
    val error: LiveEvent<String>
        get() = errorData
    val errorView: LiveEvent<Boolean>
        get() = errorViewData
    val loading: LiveData<Boolean>
        get() = loadingLiveData

    fun discoverMovie() {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            try {
                val result = movieUseCase.discoverMovie()
                movieLiveData.postValue(result)
                errorViewData.postValue(false)
            } catch (e: Throwable) {
                errorData.postValue("Нам не удалось обработать ваш запрос. Произошла ошибка: ${e.message}. Попробуйте еще раз")
                errorViewData.postValue(true)
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }


    fun getMovieList() {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            try {
                val result = movieUseCase.getMovieList()
                movieListLiveData.postValue(result)
                errorViewData.postValue(false)
            } catch (e: Throwable) {
                errorData.postValue("Нам не удалось обработать ваш запрос. Произошла ошибка: ${e.message}. Попробуйте еще раз")
                errorViewData.postValue(true)
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            try {
                val result = movieUseCase.getMovie(movieId)
                movieDetailsLiveData.postValue(result)
                errorViewData.postValue(false)
            } catch (e: Throwable) {
                errorData.postValue("Нам не удалось обработать ваш запрос. Произошла ошибка: ${e.message}. Попробуйте еще раз")
                errorViewData.postValue(true)
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            try {
                val result = movieUseCase.searchMovie(query)
                movieLiveData.postValue(result)
                val listSearchMovie = result.results
                movieListLiveData.postValue(listSearchMovie)
                errorViewData.postValue(false)
                if (listSearchMovie.isNullOrEmpty()) {
                    errorData.postValue("По запросу \"$query\" ничего не найдено")
                    errorViewData.postValue(true)
                }
            } catch (e: Throwable) {
                errorData.postValue("Нам не удалось обработать ваш запрос. Произошла ошибка: ${e.message}. Попробуйте еще раз")
                errorViewData.postValue(true)
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }
}