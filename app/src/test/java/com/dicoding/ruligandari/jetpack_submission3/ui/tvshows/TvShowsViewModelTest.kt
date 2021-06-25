package com.dicoding.ruligandari.jetpack_submission3.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest{
    private lateinit var viewModel: TvShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var tvShowsObserver: Observer<Resource<PagedList<TvShowsEntity>>>

    @Mock
    private lateinit var pagedListTvShows: PagedList<TvShowsEntity>

    @Before
    fun setUp(){
        viewModel = TvShowsViewModel(dataRepository)
    }

    @Test
    fun testGetTvShows(){
        val dummyTvShows = Resource.success(pagedListTvShows)
        `when`(dummyTvShows.data?.size).thenReturn(10)
        val tvShows = MutableLiveData<Resource<PagedList<TvShowsEntity>>>()
        tvShows.value = dummyTvShows

        `when`(dataRepository.getTvShows()).thenReturn(tvShows)
        val tvShowsEntities = viewModel.getTvShows().value?.data
        verify(dataRepository).getTvShows()

        TestCase.assertNotNull(tvShowsEntities)
        TestCase.assertEquals(10, tvShowsEntities?.size)

        viewModel.getTvShows().observeForever(tvShowsObserver)
        verify(tvShowsObserver).onChanged(dummyTvShows)
    }
}