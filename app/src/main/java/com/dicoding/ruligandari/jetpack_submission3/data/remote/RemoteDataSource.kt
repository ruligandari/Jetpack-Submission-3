package com.dicoding.ruligandari.jetpack_submission3.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.ruligandari.jetpack_submission3.BuildConfig.API_KEY
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.data.remote.response.MoviesResponse
import com.dicoding.ruligandari.jetpack_submission3.data.remote.response.TvShowsResponse
import com.dicoding.ruligandari.jetpack_submission3.network.ApiClient
import com.dicoding.ruligandari.jetpack_submission3.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    private val apiKey = API_KEY

    companion object{
        private var INSTANCE: RemoteDataSource? = null
        private val TAG = RemoteDataSource::class.java.toString()

        fun getInstance(): RemoteDataSource{
            if (INSTANCE == null)
                INSTANCE = RemoteDataSource()
                return INSTANCE!!
        }
    }

    fun getMovies(): LiveData<ApiResponse<List<MoviesEntity>>>{
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MoviesEntity>>>()
        val client = ApiClient.getApiService().getMovies(apiKey)

        client.enqueue(object: Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                resultMovies.value =
                    ApiResponse.success(response.body()?.results as List<MoviesEntity>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.d(TAG, "onFailure:${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovies
    }

    fun getMoviesDetail(id: Int): LiveData<ApiResponse<MoviesEntity>>{
        EspressoIdlingResource.increment()
        val resultDetailMovies = MutableLiveData<ApiResponse<MoviesEntity>>()
        val client = ApiClient.getApiService().getMoviesDetail(id, apiKey)

        client.enqueue(object: Callback<MoviesEntity>{
            override fun onResponse(call: Call<MoviesEntity>, response: Response<MoviesEntity>) {
                resultDetailMovies.value = ApiResponse.success(response.body() as MoviesEntity)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MoviesEntity>, t: Throwable) {
                Log.d(TAG, "onFailure :${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultDetailMovies
    }

    fun getTvShows(): LiveData<ApiResponse<List<TvShowsEntity>>>{
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowsEntity>>>()
        val client = ApiClient.getApiService().getTvShows(apiKey)
        client.enqueue(object : Callback<TvShowsResponse>{
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                resultTvShows.value = ApiResponse.success(response.body()?.results as List<TvShowsEntity>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure :${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultTvShows
    }

    fun getTvShowsDetail(id: Int) : LiveData<ApiResponse<TvShowsEntity>>{
        EspressoIdlingResource.increment()
        val resultDetailTvShows = MutableLiveData<ApiResponse<TvShowsEntity>>()
        val client = ApiClient.getApiService().getTvShowsDetail(id, apiKey)

        client.enqueue(object: Callback<TvShowsEntity>{
            override fun onResponse(call: Call<TvShowsEntity>, response: Response<TvShowsEntity>) {
                resultDetailTvShows.value = ApiResponse.success(response.body() as TvShowsEntity)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowsEntity>, t: Throwable) {
                Log.d(TAG, "onFailure :${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultDetailTvShows
    }
}