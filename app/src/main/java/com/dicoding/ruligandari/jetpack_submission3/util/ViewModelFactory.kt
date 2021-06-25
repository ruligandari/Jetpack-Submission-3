package com.dicoding.ruligandari.jetpack_submission3.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.di.Injection
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.viewmodel.FavoriteMoviesViewModel
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.viewmodel.FavoriteTvShowsViewModel
import com.dicoding.ruligandari.jetpack_submission3.ui.movie.MoviesViewModel
import com.dicoding.ruligandari.jetpack_submission3.ui.tvshows.TvShowsViewModel

class ViewModelFactory(private val dataRepository: DataRepository): ViewModelProvider.NewInstanceFactory() {
    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: ViewModelFactory(Injection.provideDataRepository(context)).apply {
                instance = this
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MoviesViewModel::class.java) ->
                MoviesViewModel(dataRepository) as T
            modelClass.isAssignableFrom(TvShowsViewModel::class.java) ->
                TvShowsViewModel(dataRepository) as T
            modelClass.isAssignableFrom(FavoriteMoviesViewModel::class.java)->
                FavoriteMoviesViewModel(dataRepository) as T
            modelClass.isAssignableFrom(FavoriteTvShowsViewModel::class.java) ->
                FavoriteTvShowsViewModel(dataRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel:" +modelClass.name)
        }
    }
}


