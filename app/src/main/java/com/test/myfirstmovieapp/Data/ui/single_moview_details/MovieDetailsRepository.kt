package com.test.myfirstmovieapp.Data.ui.single_moview_details

import androidx.lifecycle.LiveData
import com.test.myfirstmovieapp.Data.Api.TheMovieDBInterface
import com.test.myfirstmovieapp.Data.Repository.MovieDetailsNetworkDataSource
import com.test.myfirstmovieapp.Data.Repository.NetworkState
import com.test.myfirstmovieapp.Data.VO.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource


    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}