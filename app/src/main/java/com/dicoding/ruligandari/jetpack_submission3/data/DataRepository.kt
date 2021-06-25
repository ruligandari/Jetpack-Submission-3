package com.dicoding.ruligandari.jetpack_submission3.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.ruligandari.jetpack_submission3.data.local.LocalDataSource
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.data.remote.ApiResponse
import com.dicoding.ruligandari.jetpack_submission3.data.remote.RemoteDataSource
import com.dicoding.ruligandari.jetpack_submission3.util.AppExecutor
import com.dicoding.ruligandari.jetpack_submission3.vo.Resource
import java.util.*

class DataRepository(private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource, val mExecutor: AppExecutor): DataSource {

    companion object{
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource,
            mExecutor: AppExecutor
        ): DataRepository?{
            if (INSTANCE == null) synchronized(DataRepository::class.java){
                if (INSTANCE == null)
                    INSTANCE = DataRepository(localDataSource,remoteDataSource,mExecutor)
            }
            return INSTANCE
        }
    }
    override fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object:
        NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesEntity>>(mExecutor){
            override fun loadDataFromDB(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MoviesEntity>>> =
                remoteDataSource.getMovies()

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
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMoviesDetail(id: Int): LiveData<Resource<MoviesEntity>> {
        return object: NetworkBoundResource<MoviesEntity, MoviesEntity>(mExecutor){
            override fun loadDataFromDB(): LiveData<MoviesEntity> {
                return localDataSource.getMoviesDetail(id)
            }

            override fun shouldFetch(data: MoviesEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<MoviesEntity>> {
                return remoteDataSource.getMoviesDetail(id)
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
               localDataSource.updateMovies(movie)
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowsEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowsEntity>, List<TvShowsEntity>>(mExecutor){
            override fun loadDataFromDB(): LiveData<PagedList<TvShowsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .setInitialLoadSizeHint(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowsEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowsEntity>>> {
                return remoteDataSource.getTvShows()
            }

            override fun saveCallResult(data: List<TvShowsEntity>) {
                val tvShowList = ArrayList<TvShowsEntity>()
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
                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getTvShowsDetail(id: Int): LiveData<Resource<TvShowsEntity>> {
        return object : NetworkBoundResource<TvShowsEntity, TvShowsEntity>(mExecutor) {
            override fun loadDataFromDB(): LiveData<TvShowsEntity> {
                return localDataSource.getTvShowsDetail(id)
            }

            override fun shouldFetch(data: TvShowsEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<TvShowsEntity>> {
                return remoteDataSource.getTvShowsDetail(id)
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
                localDataSource.updateTvShows(tvshow)
            }

        }.asLiveData()

    }

    override fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(),config).build()
    }

    override fun setFavoriteTvShows(tv: TvShowsEntity, isFavorite: Boolean) {
        mExecutor.diskIO().execute { localDataSource.setFavoriteTvShows(tv, isFavorite) }
    }

    override fun setFavoritesMovies(movie: MoviesEntity, isFavorite: Boolean) {
        mExecutor.diskIO().execute { localDataSource.setFavoriteMovies(movie,isFavorite) }
    }
}