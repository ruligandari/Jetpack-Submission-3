package com.dicoding.ruligandari.jetpack_submission3.ui.movie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ruligandari.jetpack_submission3.BuildConfig.IMAGE_URL
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.MoviesEntity
import com.dicoding.ruligandari.jetpack_submission3.databinding.ItemMoviesBinding
import com.dicoding.ruligandari.jetpack_submission3.ui.MainActivity.Companion.EXTRA_ID
import com.dicoding.ruligandari.jetpack_submission3.util.Utilization.Companion.glideOption

class MovieAdapter(context: Context?):
    PagedListAdapter<MoviesEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK){

    private val activity = context as Activity

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviesEntity>(){
            override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean
               = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean =
              oldItem == newItem

        }
    }

    inner class MovieViewHolder(private val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviesEntity){
            with(binding){
                tvTitle.text = movie.title
                tvDescription.text = movie.overview
                tvRating.text = movie.vote_average.toString()
                Glide.with(itemView)
                    .load(IMAGE_URL + movie.poster_path)
                    .centerCrop()
                    .apply(glideOption)
                    .into(imgPoster)
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                    intent.putExtra(EXTRA_ID, movie.id)
                    activity.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null ) holder.bind(movie)
    }
}