package com.dicoding.ruligandari.jetpack_submission3

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.DataSource
import com.dicoding.ruligandari.jetpack_submission3.data.NetworkBoundResource
import com.dicoding.ruligandari.jetpack_submission3.data.local.LocalDataSource
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.data.remote.ApiResponse
import com.dicoding.ruligandari.jetpack_submission3.data.remote.RemoteDataSource
import com.dicoding.ruligandari.jetpack_submission3.util.AppExecutor
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource

class FakeRepository (
    private val remoteMovieDataSource: RemoteDataSource,
    private val localMovieDataSource: LocalDataSource,
    private val appThreadExecutor: AppExecutor
): DataSource {

    override fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object : NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesEntity>>(appThreadExecutor){
            override fun loadDataFromDB(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localMovieDataSource.getMovies(), config).build()
            }

            override fun saveCallResult(data: List<MoviesEntity>) {
                val movieList = ArrayList<MoviesEntity>()
                for (response in data){
                    val movie = MoviesEntity(
                        response.id,
                        response.poster_path,
                        response.backdrop_path,
                        response.title,
                        response.overview,
                        response.release_date,
                        response.vote_average
                    )
                    movieList.add(movie)
                }
                localMovieDataSource.insertMovies(movieList)
            }

            override fun createCall(): LiveData<ApiResponse<List<MoviesEntity>>> =
                remoteMovieDataSource.getMovies()


            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

        }.asLiveData()
    }

    override fun getMoviesDetail(id: Int): LiveData<Resource<MoviesEntity>> {
        return object: NetworkBoundResource<MoviesEntity, MoviesEntity>(appThreadExecutor){
            override fun loadDataFromDB(): LiveData<MoviesEntity> {
                return localMovieDataSource.getMoviesDetail(id)
            }

            override fun shouldFetch(data: MoviesEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<MoviesEntity>> {
                return remoteMovieDataSource.getMoviesDetail(id)
            }

            override fun saveCallResult(data: MoviesEntity) {
                val movie = MoviesEntity(
                    data.id,
                    data.poster_path,
                    data.backdrop_path,
                    data.title,
                    data.overview,
                    data.release_date,
                    data.vote_average
                )
                localMovieDataSource.updateMovies(movie)
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowsEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowsEntity>, List<TvShowsEntity>>(appThreadExecutor){
            override fun loadDataFromDB(): LiveData<PagedList<TvShowsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .setInitialLoadSizeHint(4)
                    .build()
                return LivePagedListBuilder(localMovieDataSource.getTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowsEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowsEntity>>> {
                return remoteMovieDataSource.getTvShows()
            }

            override fun saveCallResult(data: List<TvShowsEntity>) {
                val tvShowList = java.util.ArrayList<TvShowsEntity>()
                for (response in data){
                    val tvshow = TvShowsEntity(
                        response.id,
                        response.poster_path,
                        response.backdrop_path,
                        response.name,
                        response.first_air_date,
                        response.overview,
                        response.vote_average
                    )
                    tvShowList.add(tvshow)
                }
                localMovieDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getTvShowsDetail(id: Int): LiveData<Resource<TvShowsEntity>> {
        return object : NetworkBoundResource<TvShowsEntity, TvShowsEntity>(appThreadExecutor) {
            override fun loadDataFromDB(): LiveData<TvShowsEntity> {
                return localMovieDataSource.getTvShowsDetail(id)
            }

            override fun shouldFetch(data: TvShowsEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<TvShowsEntity>> {
                return remoteMovieDataSource.getTvShowsDetail(id)
            }

            override fun saveCallResult(data: TvShowsEntity) {
                val tvshow = TvShowsEntity(
                    data.id,
                    data.poster_path,
                    data.backdrop_path,
                    data.name,
                    data.first_air_date,
                    data.overview,
                    data.vote_average

                )
                localMovieDataSource.updateTvShows(tvshow)
            }

        }.asLiveData()

    }

    override fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localMovieDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localMovieDataSource.getFavoriteTvShows(),config).build()
    }

    override fun setFavoriteTvShows(tv: TvShowsEntity, isFavorite: Boolean) {
        appThreadExecutor.diskIO().execute { localMovieDataSource.setFavoriteTvShows(tv, isFavorite) }
    }

    override fun setFavoritesMovies(movie: MoviesEntity, isFavorite: Boolean) {
        appThreadExecutor.diskIO().execute { localMovieDataSource.setFavoriteMovies(movie,isFavorite) }
    }
}