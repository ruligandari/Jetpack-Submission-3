package com.dicoding.ruligandari.jetpack_submission3.data.remote.response

import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @SerializedName("results")
    val results: List<TvShowsEntity>
)