package com.test.myfirstmovieapp.Data.ui.popular_movie

import android.arch.lifecycle.Transformations
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.test.myfirstmovieapp.Data.Api.POST_PER_PAGE
import com.test.myfirstmovieapp.Data.Api.TheMovieDBInterface
import com.test.myfirstmovieapp.Data.Repository.MovieDataSource
import com.test.myfirstmovieapp.Data.Repository.MovieDataSourceFactory
import com.test.myfirstmovieapp.Data.Repository.NetworkState
import com.test.myfirstmovieapp.Data.VO.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory =
            MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList

    }

    fun getNetworkState(): LiveData<NetworkState>? {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}