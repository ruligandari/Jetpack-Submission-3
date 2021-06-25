package com.dicoding.ruligandari.jetpack_submission3.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource

class MoviesViewModel(private val dataRepository: DataRepository): ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>> = dataRepository.getMovies()

    private val selectedMoviesId = MutableLiveData<Int>()

    fun selectedDetail(id: Int) {
        this.selectedMoviesId.value = id
    }

    val selectedMovies: LiveData<Resource<MoviesEntity>> =
        Transformations.switchMap(selectedMoviesId){
            id -> selectedMoviesId.value.let { dataRepository.getMoviesDetail(id) }
        }

    fun setFavoriteMovie(){
        val movieResource = selectedMovies.value
        if (movieResource != null){
            val moviesEntity = movieResource.data
            if (moviesEntity != null){
                val newState = !moviesEntity.isFavorite
                dataRepository.setFavoritesMovies(moviesEntity,newState)
            }
        }
    }

}
