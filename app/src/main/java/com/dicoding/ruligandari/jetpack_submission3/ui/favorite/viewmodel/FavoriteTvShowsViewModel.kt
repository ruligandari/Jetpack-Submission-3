package com.dicoding.ruligandari.jetpack_submission3.ui.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity

class FavoriteTvShowsViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntity>> =
        dataRepository.getFavoriteTvShows()

    fun setFavoriteTvShows(tvShowsEntity: TvShowsEntity){
        val isFavorite = !tvShowsEntity.isfavorite
        dataRepository.setFavoriteTvShows(tvShowsEntity, isFavorite)
    }
}