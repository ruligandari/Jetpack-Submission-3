package com.dicoding.ruligandari.jetpack_submission3.ui.tvshows

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.ruligandari.jetpack_submission3.BuildConfig.IMAGE_URL
import com.dicoding.ruligandari.jetpack_submission3.R
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.databinding.ActivityTvShowsDetailBinding
import com.dicoding.ruligandari.jetpack_submission3.ui.MainActivity.Companion.EXTRA_ID
import com.dicoding.ruligandari.jetpack_submission3.util.Utilization.Companion.glideOption
import com.dicoding.ruligandari.jetpack_submission3.util.ViewModelFactory
import com.dicoding.ruligandari.jetpack_submission3.vo.Status

class TvShowsDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTvShowsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this,factory)[TvShowsViewModel::class.java]
        val tvShowsId = intent.extras?.getInt(EXTRA_ID)
        tvShowsId?.let { viewModel.setSelectedDetail(it) }

        if (tvShowsId != null) viewModel.selectedTvShows.observe(this,{ tvShow ->
            if (tvShow != null){
                with(binding){
                    when(tvShow.status){
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            tvShow.data?.isfavorite?.let { setFavoriteState(it, binding) }
                            tvShow.data?.let { loadTvShowsDetail(it, binding) }
                            fabFavorite.setOnClickListener {
                                viewModel.setFavoriteTvSHows()
                                if (tvShow.data?.isfavorite!!)
                                    Toast.makeText(
                                        this@TvShowsDetailActivity,
                                        "Success Deleted from Favorite!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else   Toast.makeText(
                                    this@TvShowsDetailActivity,
                                    "Success Added to Favorite!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@TvShowsDetailActivity,
                                "Terjadi Kesalahan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        })
    }

    private fun setFavoriteState(state: Boolean, binding: ActivityTvShowsDetailBinding){
        with(binding){
            if (state) fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@TvShowsDetailActivity,
                    R.drawable.ic_favorite
                )
            )
            else fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@TvShowsDetailActivity,
                    R.drawable.ic_favorite_border
                )
            )
        }
    }

    private fun loadTvShowsDetail(tvShowDetail: TvShowsEntity, binding: ActivityTvShowsDetailBinding){
        binding.progressBar.visibility = View.GONE

        Glide.with(this)
            .load(IMAGE_URL + tvShowDetail.backdrop_path)
            .apply(glideOption)
            .into(binding.imgPosterBig)
        Glide.with(this)
            .load(IMAGE_URL + tvShowDetail.poster_path)
            .apply(glideOption)
            .into(binding.imgPoster)
        binding.tvItemTitle.text = tvShowDetail.name
        binding.tvItemRelease.text = tvShowDetail.first_air_date
        binding.tvItemRating.text = tvShowDetail.vote_average.toString()
        binding.tvItemDescription.text = tvShowDetail.overview
    }

}
