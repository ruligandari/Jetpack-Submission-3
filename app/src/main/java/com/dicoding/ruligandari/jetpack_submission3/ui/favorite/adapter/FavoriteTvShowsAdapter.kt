package com.dicoding.ruligandari.jetpack_submission3.ui.favorite.adapter

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
import com.dicoding.ruligandari.jetpack_submission3.data.local.entity.TvShowsEntity
import com.dicoding.ruligandari.jetpack_submission3.databinding.ItemTvShowsBinding
import com.dicoding.ruligandari.jetpack_submission3.ui.MainActivity.Companion.EXTRA_ID
import com.dicoding.ruligandari.jetpack_submission3.ui.tvshows.TvShowsDetailActivity
import com.dicoding.ruligandari.jetpack_submission3.util.Utilization.Companion.glideOption

class FavoriteTvShowsAdapter(context: Context?): PagedListAdapter<TvShowsEntity, FavoriteTvShowsAdapter.TvShowViewHolder>(
    DIFF_CALLBACK
) {
    private val activity = context as Activity

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowsEntity>(){
            override fun areItemsTheSame(oldItem: TvShowsEntity, newItem: TvShowsEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TvShowsEntity,
                newItem: TvShowsEntity
            ): Boolean =
                oldItem == newItem

        }
    }
    inner class TvShowViewHolder(private val binding: ItemTvShowsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowsEntity){
            with(binding){
                tvTitleTvshows.text = tvShow.name
                tvDescription.text = tvShow.overview
                tvRating.text = tvShow.vote_average.toString()
                Glide.with(itemView)
                    .load(IMAGE_URL + tvShow.poster_path)
                    .apply(glideOption)
                    .into(imgPoster)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, TvShowsDetailActivity::class.java)
                    intent.putExtra(EXTRA_ID, tvShow.id)
                    activity.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = ItemTvShowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) holder.bind(tvShow)
    }

}