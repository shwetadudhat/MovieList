package com.test.myfirstmovieapp.Data.ui.popular_movie

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository : MoviePageListRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState by lazy {
        movieRepository.getNetworkState()
    }


    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}