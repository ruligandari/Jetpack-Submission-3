package com.dicoding.ruligandari.jetpack_submission3.ui.favorite.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowsViewModelTest {
    private lateinit var viewModel: FavoriteTvShowsViewModel

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowsEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowsEntity>

    @Before
    fun setUp(){
        viewModel = FavoriteTvShowsViewModel(dataRepository)
    }

    @Test
    fun testGetFavoriteeTvShows(){
        val dummyTvShows = pagedList
                Mockito.`when`(dummyTvShows.size).thenReturn(5)
        val tvShows = MutableLiveData<PagedList<TvShowsEntity>>()
        tvShows.value = dummyTvShows

        Mockito.`when`(dataRepository.getFavoriteTvShows()).thenReturn(tvShows)
        val tvShowsEntities = viewModel.getFavoriteTvShows().value
        verify(dataRepository).getFavoriteTvShows()

        TestCase.assertNotNull(tvShowsEntities)
        TestCase.assertEquals(5, tvShowsEntities?.size)

        viewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}