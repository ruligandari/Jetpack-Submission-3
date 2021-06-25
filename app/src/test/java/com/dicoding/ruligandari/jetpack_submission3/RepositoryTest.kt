package com.dicoding.ruligandari.jetpack_submission3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.ruligandari.jetpack_submission3.data.local.LocalDataSource
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.data.remote.RemoteDataSource
import com.dicoding.ruligandari.jetpack_submission3.util.AppExecutor
import com.dicoding.ruligandari.jetpack_submission3.util.AppExecutorTest
import com.dicoding.ruligandari.jetpack_submission3.util.DummyData
import com.dicoding.ruligandari.jetpack_submission3.util.UtilPagedList
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
class RepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutor = mock(AppExecutor::class.java)
    private val testExecutor: AppExecutor = AppExecutor(AppExecutorTest(), AppExecutorTest(), AppExecutorTest())
    private val dataRepository = FakeRepository(remote, local, appExecutor)
    private val movieResponses = DummyData.generateDummyMovies()
    private val movieId = movieResponses[0].id
    private val tvShowResponses = DummyData.generateDummyTvShows()
    private val tvShowId = tvShowResponses[0].id

    @Test
    fun testGetAllMovies(){
        val dataMovieSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when` (local.getMovies()).thenReturn(dataMovieSourceFactory)
        dataRepository.getMovies()

        val movieEntities = Resource.success(UtilPagedList.mockPagedList(DummyData.generateDummyMovies()))
        com.nhaarman.mockitokotlin2.verify(local).getMovies()
        TestCase.assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetDetailMovies(){
        val dummyDetailMovie = MutableLiveData<MoviesEntity>()
        dummyDetailMovie.value = DummyData.generateDummyMovies()[0]
        `when` (local.getMoviesDetail(movieId)).thenReturn(dummyDetailMovie)

        val resultMovie = LiveDataTestUtil.getValue(dataRepository.getMoviesDetail(movieId))
        com.nhaarman.mockitokotlin2.verify(local).getMoviesDetail(movieId)
        TestCase.assertNotNull(resultMovie.data)
        assertEquals(movieResponses[0].title, resultMovie.data?.title)
    }
    @Test
    fun testGetAllTvShows() {
        val dataTvShowSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowsEntity>
        `when`(local.getTvShows()).thenReturn(dataTvShowSourceFactory)
        dataRepository.getTvShows()

        val tvShowEntities =
            Resource.success(UtilPagedList.mockPagedList(DummyData.generateDummyTvShows()))

        com.nhaarman.mockitokotlin2.verify(local).getTvShows()
        TestCase.assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }
    @Test
    fun testGetDetailTvShows(){
        val dummyDetailTvShows = MutableLiveData<TvShowsEntity>()
        dummyDetailTvShows.value = DummyData.generateDummyTvShows()[0]
        `when` (local.getTvShowsDetail(tvShowId)).thenReturn(dummyDetailTvShows)

        val resultTvShows = LiveDataTestUtil.getValue(dataRepository.getTvShowsDetail(tvShowId))
        com.nhaarman.mockitokotlin2.verify(local).getTvShowsDetail(tvShowId)
        TestCase.assertNotNull(resultTvShows.data)
        assertEquals(tvShowResponses[0].name, resultTvShows.data?.name)
    }
    @Test
    fun testGetFavoritesMovies() {
        val dataMovieSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataMovieSourceFactory)
        dataRepository.getFavoriteMovies()
        val movieEntities =
            Resource.success(UtilPagedList.mockPagedList(DummyData.generateDummyMovies()))

        com.nhaarman.mockitokotlin2.verify(local).getFavoriteMovies()
        TestCase.assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetFavoritesTvShows() {
        val dataTvShowSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowsEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(dataTvShowSourceFactory)
        dataRepository.getFavoriteTvShows()
        val tvShowEntities =
            Resource.success(UtilPagedList.mockPagedList(DummyData.generateDummyTvShows()))

        com.nhaarman.mockitokotlin2.verify(local).getFavoriteTvShows()
        TestCase.assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun testSetFavoritesMovies() {
        val dataMovieDummy = DummyData.generateDummyMovies()[0].copy(isFavorite = false)
        val newFavState: Boolean = !dataMovieDummy.isFavorite

        `when`(appExecutor.diskIO()).thenReturn(testExecutor.diskIO())
        doNothing().`when`(local).setFavoriteMovies(dataMovieDummy, newFavState)

        dataRepository.setFavoritesMovies(dataMovieDummy, newFavState)
        verify(local, times(1)).setFavoriteMovies(dataMovieDummy, newFavState)
    }

    @Test
    fun testSetUnFavoritesMovies() {
        val dataMovieDummy = DummyData.generateDummyMovies()[0].copy(isFavorite = true)
        val newFavState: Boolean = !dataMovieDummy.isFavorite

        `when`(appExecutor.diskIO()).thenReturn(testExecutor.diskIO())
        doNothing().`when`(local).setFavoriteMovies(dataMovieDummy, newFavState)

        dataRepository.setFavoritesMovies(dataMovieDummy, newFavState)
        verify(local, times(1)).setFavoriteMovies(dataMovieDummy, newFavState)
    }

    @Test
    fun testSetFavoritesTvShows() {
        val dataTvShowDummy = DummyData.generateDummyTvShows()[0].copy(isfavorite = false)
        val newFavState: Boolean = !dataTvShowDummy.isfavorite

        `when`(appExecutor.diskIO()).thenReturn(testExecutor.diskIO())
        doNothing().`when`(local).setFavoriteTvShows(dataTvShowDummy, newFavState)

        dataRepository.setFavoriteTvShows(dataTvShowDummy, newFavState)
        verify(local, times(1)).setFavoriteTvShows(dataTvShowDummy, newFavState)
    }

    @Test
    fun testSetUnFavoritesTvShows() {
        val dataTvShowDummy = DummyData.generateDummyTvShows()[0].copy(isfavorite = true)
        val newFavState: Boolean = !dataTvShowDummy.isfavorite

        `when`(appExecutor.diskIO()).thenReturn(testExecutor.diskIO())
        doNothing().`when`(local).setFavoriteTvShows(dataTvShowDummy, newFavState)

        dataRepository.setFavoriteTvShows(dataTvShowDummy, newFavState)
        verify(local, times(1)).setFavoriteTvShows(dataTvShowDummy, newFavState)
    }

}