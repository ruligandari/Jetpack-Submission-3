package com.dicoding.ruligandari.jetpack_submission3.data.remote.response

import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.google.gson.annotations.SerializedName


data class MoviesResponse (
        @SerializedName("results")
        val results: List<MoviesEntity>
        )