package com.dicoding.ruligandari.jetpack_submission3.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.ui.movie.MoviesViewModel
import com.dicoding.ruligandari.jetpack_submission3.util.DummyData
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesDetailViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private val dummyMovies = DummyData.generateDummyMovies()[0]
    private val id = dummyMovies.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var detailObserver: Observer<Resource<MoviesEntity>>

    @Before
    fun setUp(){
        viewModel = MoviesViewModel(dataRepository)
        viewModel.selectedDetail(id)

    }

    @Test
    fun testGetMoviesEntity(){
        val movie = MutableLiveData<Resource<MoviesEntity>>()
        movie.value = Resource.success(dummyMovies)

        `when` (dataRepository.getMoviesDetail(id)).thenReturn(movie)
        viewModel.selectedMovies.observeForever(detailObserver)
        verify(detailObserver).onChanged(movie.value)
    }

    @Test
    fun testSetFavoriteMovie(){
        val dummyTestMovie: Resource<MoviesEntity> = Resource.success(dummyMovies)
        val movie: MutableLiveData<Resource<MoviesEntity>> = MutableLiveData<Resource<MoviesEntity>>()
        movie.value = dummyTestMovie
        movie.value?.data?.isFavorite = false

        `when`(dataRepository.getMoviesDetail(id)).thenReturn(movie)
        viewModel.selectedMovies.observeForever(detailObserver)

        dummyTestMovie.data?.let {
            Mockito.doNothing().`when`(dataRepository).setFavoritesMovies(it, !it.isFavorite)
            viewModel.setFavoriteMovie()
            Mockito.verify(dataRepository).setFavoritesMovies(it, !it.isFavorite)
        }
    }

    @Test
    fun testSetUnFavoriteMovie(){
        val dummyTestMovies: Resource<MoviesEntity> = Resource.success(dummyMovies)
        val movie: MutableLiveData<Resource<MoviesEntity>> = MutableLiveData<Resource<MoviesEntity>>()
        movie.value = dummyTestMovies
        movie.value?.data?.isFavorite = true

        `when`(dataRepository.getMoviesDetail(id)).thenReturn(movie)
        viewModel.selectedMovies.observeForever(detailObserver)

        dummyTestMovies.data?.let {
            doNothing().`when`(dataRepository).setFavoritesMovies(it, !it.isFavorite)
            viewModel.setFavoriteMovie()
            verify(dataRepository).setFavoritesMovies(it, !it.isFavorite)
        }
    }
}