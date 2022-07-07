package com.annti.movieapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.movieapp.data.model.Results
import com.annti.movieapp.domain.FavoriteMovieUseCase
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(
    private val favMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {

    private val moviesMutableState = MutableStateFlow<List<Results>>(emptyList())
    val movies: StateFlow<List<Results>> = moviesMutableState

    private val heartPaintedOverLiveData = MutableLiveData<Boolean>()
    private var errorData = LiveEvent<String>()
    private var errorViewData = LiveEvent<Boolean>()
    private var loadingLiveData = MutableLiveData<Boolean>()

    val heartPaintedOverButton: LiveData<Boolean>
        get() = heartPaintedOverLiveData
    val error: LiveEvent<String>
        get() = errorData
    val errorView: LiveEvent<Boolean>
        get() = errorViewData
    val loading: LiveData<Boolean>
        get() = loadingLiveData

    fun getFavorites() {
        favMovieUseCase.getFavoriteMovies()
            .onEach {
                moviesMutableState.value = it
                if (it.isNullOrEmpty()){
                    errorData.postValue("Еще нет избранных фильмов")
                    errorViewData.postValue(true)
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = favMovieUseCase.getMovieId(movieId)
                if (result != null) {
                    heartPaintedOverLiveData.postValue(true)
                } else heartPaintedOverLiveData.postValue(false)
            } catch (e: Throwable) {
                errorData.postValue("Нам не удалось обработать ваш запрос. Произошла ошибка: ${e.message}. Попробуйте еще раз")
                errorViewData.postValue(true)
            }
        }
    }

    fun saveMovie(movie: Results) {
        viewModelScope.launch {
            try {
                favMovieUseCase.saveMovie(movie)
                heartPaintedOverLiveData.postValue(true)
            } catch (e: Throwable) {
                errorData.postValue("Нам не удалось обработать ваш запрос. Произошла ошибка: ${e.message}. Попробуйте еще раз")
                errorViewData.postValue(true)
            }
        }
    }

    fun removeFavoriteMovie(movie: Results) {
        viewModelScope.launch {
            try {
                favMovieUseCase.removeMovie(movie)
                heartPaintedOverLiveData.postValue(false)
                errorViewData.postValue(true)
            } catch (e: Throwable) {
                errorData.postValue("Нам не удалось обработать ваш запрос. Произошла ошибка: ${e.message}. Попробуйте еще раз")
                errorViewData.postValue(true)
            }
        }
    }

    fun removeAllFavoritesMovie() {
        viewModelScope.launch {
            try {
                favMovieUseCase.removeAllMovies()
            } catch (e: Throwable) {
                errorData.postValue("Нам не удалось обработать ваш запрос. Произошла ошибка: ${e.message}. Попробуйте еще раз")
                errorViewData.postValue(true)
            }
        }
    }
}