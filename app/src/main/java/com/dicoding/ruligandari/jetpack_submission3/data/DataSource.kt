package com.dicoding.ruligandari.jetpack_submission3.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource

interface DataSource {
    fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>>
    fun getMoviesDetail(id: Int): LiveData<Resource<MoviesEntity>>
    fun getTvShows(): LiveData<Resource<PagedList<TvShowsEntity>>>
    fun getTvShowsDetail(id: Int): LiveData<Resource<TvShowsEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>>
    fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntity>>

    fun setFavoritesMovies(movie: MoviesEntity, isFavorite: Boolean)
    fun setFavoriteTvShows(tv: TvShowsEntity, isFavorite: Boolean)
}