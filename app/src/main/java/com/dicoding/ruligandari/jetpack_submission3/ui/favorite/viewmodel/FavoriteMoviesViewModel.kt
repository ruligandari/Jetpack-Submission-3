package com.dicoding.ruligandari.jetpack_submission3.ui.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity

class FavoriteMoviesViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>> =
        dataRepository.getFavoriteMovies()

    fun setFavoriteMovies(moviesEntity: MoviesEntity){
        val isFavorite = !moviesEntity.isFavorite
        dataRepository.setFavoritesMovies(moviesEntity, isFavorite)
    }
}