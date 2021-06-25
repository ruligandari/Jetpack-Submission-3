package com.dicoding.ruligandari.jetpack_submission3.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvshow")
data class TvShowsEntity(
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
    val backdrop_path: String?,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    val first_air_date: String?,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String?,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val vote_average: Double?,

    @ColumnInfo(name = "isfavorite")
    var isfavorite: Boolean = false
)