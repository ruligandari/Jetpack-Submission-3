package com.dicoding.ruligandari.jetpack_submission3.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity

@Database(entities = [MoviesEntity::class, TvShowsEntity::class], version = 1 , exportSchema = false)
abstract class MyDatabase : RoomDatabase(){

    abstract fun dao(): MyDao

    companion object{
        private var INSTANCE: MyDatabase? = null

        private val slock = Any()

        fun getInstance(context: Context): MyDatabase{
            synchronized(slock){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, "movie.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE as MyDatabase
            }
        }
    }
}