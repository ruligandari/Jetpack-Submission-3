package com.dicoding.ruligandari.jetpack_submission3.di

import android.content.Context
import com.dicoding.ruligandari.jetpack_submission3.data.DataRepository
import com.dicoding.ruligandari.jetpack_submission3.data.local.LocalDataSource
import com.dicoding.ruligandari.jetpack_submission3.data.local.room.MyDatabase
import com.dicoding.ruligandari.jetpack_submission3.data.remote.RemoteDataSource
import com.dicoding.ruligandari.jetpack_submission3.util.AppExecutor

object Injection {
    fun provideDataRepository(context: Context): DataRepository{
        val localRepository = LocalDataSource(
        MyDatabase.getInstance(context).dao()
        )
        val remoteRepository = RemoteDataSource.getInstance()
        val mExecutor = AppExecutor()
        return remoteRepository.let { DataRepository.getInstance(localRepository, it, mExecutor)!! }
    }
}