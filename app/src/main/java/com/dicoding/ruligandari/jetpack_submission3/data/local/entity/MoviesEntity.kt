package com.dicoding.ruligandari.jetpack_submission3.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movie")
data class MoviesEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id : Int,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val poster_path : String?,

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    val backdrop_path : String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title : String?,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview : String?,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val release_date : String?,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val vote_average : Double?,

    @ColumnInfo(name= "isfavorite")
    var isFavorite: Boolean = false
)