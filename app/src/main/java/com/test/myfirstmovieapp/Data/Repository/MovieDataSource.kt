package com.test.myfirstmovieapp.Data.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.test.myfirstmovieapp.Data.Api.FIRST_PAGE
import com.test.myfirstmovieapp.Data.Api.TheMovieDBInterface
import com.test.myfirstmovieapp.Data.VO.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int,Movie>() {
    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key){
                            callback.onResult(it.movieList, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState(Status.FAILED, "You have reached the end"))
                        }

                    },
                    {
                        networkState.postValue(NetworkState(Status.FAILED, "Something went wrong"))
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState(Status.FAILED, "Something went wrong"))
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )

    }
}