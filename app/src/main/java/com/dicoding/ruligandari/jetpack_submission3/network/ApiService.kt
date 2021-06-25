package com.dicoding.ruligandari.jetpack_submission3.network

import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.data.remote.response.MoviesResponse
import com.dicoding.ruligandari.jetpack_submission3.data.remote.response.TvShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Call<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMoviesDetail(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?
    ): Call<MoviesEntity>

    @GET("tv/popular")
    fun getTvShows(@Query("api_key")apiKey: String): Call<TvShowsResponse>

    @GET("tv/{tv_id}")
    fun getTvShowsDetail(
        @Path("tv_id") tvId: Int?,
        @Query("api_key") apiKey: String?
    ): Call<TvShowsEntity>
}