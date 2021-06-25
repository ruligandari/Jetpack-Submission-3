package com.dicoding.ruligandari.jetpack_submission3.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity

@Dao
interface MyDao {
    @Query("SELECT * FROM movie")
    fun getMovies(): DataSource.Factory<Int, MoviesEntity>

    @Query ("SELECT * FROM movie where isfavorite=1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesEntity>)

    @Update
    fun updateMovies(movies: MoviesEntity)

    @Query("SELECT * FROM movie where id= :id")
    fun getMoviesId(id: Int): LiveData<MoviesEntity>


    @Query("SELECT * FROM tvshow")
    fun getTvShows(): DataSource.Factory<Int, TvShowsEntity>

    @Query ("SELECT * FROM tvshow where isfavorite=1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowsEntity>)

    @Update
    fun updateTvShows(tvSHows: TvShowsEntity)

    @Query("SELECT * FROM tvshow where id= :id")
    fun getTvShowsId(id: Int): LiveData<TvShowsEntity>

}