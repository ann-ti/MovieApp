package com.annti.movieapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.movieapp.data.model.Movie
import com.annti.movieapp.data.model.Results
import com.annti.movieapp.domain.MovieUseCase
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class SearchViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private var errorData = LiveEvent<String>()
    private var errorViewData = LiveEvent<Boolean>()
    private var loadingLiveData = MutableLiveData<Boolean>()

    private var movieListStateFlow = MutableStateFlow<List<Results>>(emptyList())
    val movieList: StateFlow<List<Results>> = movieListStateFlow

    private val jobMutableState = MutableStateFlow<Job?>(null)
    val job: StateFlow<Job?> = jobMutableState


    val loading: LiveData<Boolean>
        get() = loadingLiveData
    val error: LiveEvent<String>
        get() = errorData
    val errorView: LiveEvent<Boolean>
        get() = errorViewData


    fun searchMovie(query: Flow<String>) {
        jobMutableState.value =
            query
                .debounce(2000)
                .filter { query ->
                    if (query.isEmpty()) {
                        emptyList<Movie>()
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .onEach {
                    loadingLiveData.postValue(true)
                }
                .mapLatest { query ->
                    movieUseCase.searchMovie(query)
                }
                .catch { exception ->
                    errorData.postValue(
                        "Нам не удалось обработать ваш запрос. " +
                                "Произошла ошибка: ${exception.message}. Попробуйте еще раз"
                    )
                    errorViewData.postValue(true)

                }
                .onEach {
                    loadingLiveData.postValue(false)
                    if (it.isNullOrEmpty()) {
                        errorData.postValue("По запросу ничего не найдено")
                        errorViewData.postValue(true)
                    } else {
                        errorViewData.postValue(false)
                    }
                    movieListStateFlow.value = it
                }
                .launchIn(viewModelScope)
    }

    fun cancelJob() {
        jobMutableState.value?.cancel()
        jobMutableState.value = null
    }
}