package com.dicoding.ruligandari.jetpack_submission3.ui.movie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.ruligandari.jetpack_submission3.BuildConfig.IMAGE_URL
import com.dicoding.ruligandari.jetpack_submission3.R
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.databinding.ActivityMoviesDetailBinding
import com.dicoding.ruligandari.jetpack_submission3.ui.MainActivity.Companion.EXTRA_ID
import com.dicoding.ruligandari.jetpack_submission3.util.Utilization.Companion.glideOption
import com.dicoding.ruligandari.jetpack_submission3.util.ViewModelFactory
import com.dicoding.ruligandari.jetpack_submission3.vo.Status

class MovieDetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMoviesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

        val movieId = intent.extras?.getInt(EXTRA_ID)

        movieId?.let { viewModel.selectedDetail(it) }

        if (movieId != null) viewModel.selectedMovies.observe(this,{
            movie -> if (movie != null){
                with(binding){
                    when(movie.status){
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            progressBar.visibility = View.GONE
                            movie.data?.isFavorite?.let { setFavoriteState(it,binding) }
                            movie.data?.let { loadMoviesDetail(it, binding) }
                            fabFavorite.setOnClickListener {
                                viewModel.setFavoriteMovie()
                                if (movie.data?.isFavorite!!) {
                                    Toast.makeText(
                                        this@MovieDetailActivity,
                                        "Deleted from Favorite!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else Toast.makeText(this@MovieDetailActivity, "Add to Favorite!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@MovieDetailActivity, "Getting Trouble", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
        })
    }

    private fun setFavoriteState(state: Boolean, binding: ActivityMoviesDetailBinding){
        with(binding){
            if (state) fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(this@MovieDetailActivity,
                    R.drawable.ic_favorite)
            )
            else fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(this@MovieDetailActivity,
                    R.drawable.ic_favorite_border)
            )
        }
    }
    private fun loadMoviesDetail(moviesDetail: MoviesEntity, binding: ActivityMoviesDetailBinding){
        binding.progressBar.visibility = View.GONE

        Glide.with(this)
            .load(IMAGE_URL + moviesDetail.backdrop_path)
            .apply(glideOption)
            .into(binding.imgPosterBig)
        Glide.with(this)
            .load(IMAGE_URL + moviesDetail.poster_path)
            .apply(glideOption)
            .into(binding.imgPoster)
        binding.tvItemTitle.text = moviesDetail.title
        binding.tvItemRelease.text = moviesDetail.release_date
        binding.tvItemRating.text = moviesDetail.vote_average.toString()
        binding.tvItemDescription.text = moviesDetail.overview
    }
}
