package com.dicoding.ruligandari.jetpack_submission3.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.ui.tvshows.TvShowsViewModel
import com.dicoding.ruligandari.jetpack_submission3.util.DummyData
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsDetailViewModelTest {

    private lateinit var viewModel: TvShowsViewModel
    private val dummyTvShows = DummyData.generateDummyTvShows()[0]
    private val id = dummyTvShows.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var detailObserver: Observer<Resource<TvShowsEntity>>

    @Before
    fun setUp(){
        viewModel = TvShowsViewModel(dataRepository)
        viewModel.setSelectedDetail(id)
    }

    @Test
    fun testGetTvShowEntity() {
        val tvShow = MutableLiveData<Resource<TvShowsEntity>>()
        tvShow.value = Resource.success(dummyTvShows)

        Mockito.`when`(dataRepository.getTvShowsDetail(id)).thenReturn(tvShow)
        viewModel.selectedTvShows.observeForever(detailObserver)
        Mockito.verify(detailObserver).onChanged(tvShow.value)
    }

    @Test
    fun testSetFavoriteTvShow() {
        val dummyTestTvShow: Resource<TvShowsEntity> = Resource.success(dummyTvShows)
        val tvshow: MutableLiveData<Resource<TvShowsEntity>> =
            MutableLiveData<Resource<TvShowsEntity>>()
        tvshow.value = dummyTestTvShow
        tvshow.value?.data?.isfavorite = false

        Mockito.`when`(dataRepository.getTvShowsDetail(id)).thenReturn(tvshow)
        viewModel.selectedTvShows.observeForever(detailObserver)

        dummyTestTvShow.data?.let {
            Mockito.doNothing().`when`(dataRepository).setFavoriteTvShows(it, !it.isfavorite)
            viewModel.setFavoriteTvSHows()
            Mockito.verify(dataRepository).setFavoriteTvShows(it, !it.isfavorite)
        }
    }

    @Test
    fun testUnFavoriteTvShow() {
        val dummyTestTvShow: Resource<TvShowsEntity> = Resource.success(dummyTvShows)
        val tvshow: MutableLiveData<Resource<TvShowsEntity>> =
            MutableLiveData<Resource<TvShowsEntity>>()
        tvshow.value = dummyTestTvShow
        tvshow.value?.data?.isfavorite = true

        Mockito.`when`(dataRepository.getTvShowsDetail(id)).thenReturn(tvshow)
        viewModel.selectedTvShows.observeForever(detailObserver)

        dummyTestTvShow.data?.let {
            Mockito.doNothing().`when`(dataRepository).setFavoriteTvShows(it, !it.isfavorite)
            viewModel.setFavoriteTvSHows()
            Mockito.verify(dataRepository).setFavoriteTvShows(it, !it.isfavorite)
        }
    }

}