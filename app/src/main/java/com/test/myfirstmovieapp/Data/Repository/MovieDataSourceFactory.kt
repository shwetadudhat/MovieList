package com.test.myfirstmovieapp.Data.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.myfirstmovieapp.Data.Api.TheMovieDBInterface
import com.test.myfirstmovieapp.Data.VO.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable): DataSource.Factory<Int, Movie>(){

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiService,compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource

    }
}