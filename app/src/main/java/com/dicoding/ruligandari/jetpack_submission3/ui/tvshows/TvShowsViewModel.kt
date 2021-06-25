package com.dicoding.ruligandari.jetpack_submission3.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource

class TvShowsViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun getTvShows(): LiveData<Resource<PagedList<TvShowsEntity>>> = dataRepository.getTvShows()
    private val selectedTvShowsId = MutableLiveData<Int>()

    fun setSelectedDetail(id: Int){
        this.selectedTvShowsId.value = id
    }

    val selectedTvShows: LiveData<Resource<TvShowsEntity>> = Transformations.switchMap(selectedTvShowsId){
        id -> selectedTvShowsId.value?.let { dataRepository.getTvShowsDetail(id) }
    }

    fun setFavoriteTvSHows(){
        val tvShowResource = selectedTvShows.value
        if (tvShowResource != null){
            val tvShowsEntity = tvShowResource.data
            if (tvShowsEntity != null){
                val newState = !tvShowsEntity.isfavorite
                dataRepository.setFavoriteTvShows(tvShowsEntity, newState)
            }
        }
    }
}