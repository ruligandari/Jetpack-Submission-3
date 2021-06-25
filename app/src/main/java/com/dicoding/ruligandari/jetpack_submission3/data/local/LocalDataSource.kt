package com.dicoding.ruligandari.jetpack_submission3.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.room.MyDao

open class LocalDataSource constructor(private val dao: MyDao){
    fun getMovies(): DataSource.Factory<Int, MoviesEntity> = dao.getMovies()
    fun getMoviesDetail(moviesId: Int): LiveData<MoviesEntity> = dao.getMoviesId(moviesId)
    fun insertMovies(movies: List<MoviesEntity>){
        dao.insertMovies(movies)
    }
    fun getFavoriteMovies(): DataSource.Factory<Int, MoviesEntity> = dao.getFavoriteMovies()
    fun setFavoriteMovies(movies: MoviesEntity, isFavorite: Boolean){
        movies.isFavorite = isFavorite
        dao.updateMovies(movies)
    }

    fun getTvShows(): DataSource.Factory<Int, TvShowsEntity> = dao.getTvShows()
    fun getTvShowsDetail(tvShowsId: Int ): LiveData<TvShowsEntity> = dao.getTvShowsId(tvShowsId)
    fun insertTvShows(tvshows: List<TvShowsEntity>){
        dao.insertTvShows(tvshows)
    }
    fun getFavoriteTvShows() : DataSource.Factory<Int, TvShowsEntity> = dao.getFavoriteTvShows()
    fun setFavoriteTvShows(tvshows: TvShowsEntity, isFavorite: Boolean){
        tvshows.isfavorite = isFavorite
        dao.updateTvShows(tvshows)
    }

    fun updateMovies(movie: MoviesEntity) = dao.updateMovies(movie)
    fun updateTvShows(tvshows: TvShowsEntity)= dao.updateTvShows(tvshows)

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(mDao: MyDao): LocalDataSource =
            INSTANCE?: LocalDataSource(mDao).apply { INSTANCE = this }
    }
}