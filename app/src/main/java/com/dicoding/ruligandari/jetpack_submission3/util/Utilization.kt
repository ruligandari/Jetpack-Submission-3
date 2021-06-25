package com.dicoding.ruligandari.jetpack_submission3.util

import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.dicoding.ruligandari.jetpack_submission3.R

class Utilization {
    companion object{
        val glideOption: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image)
            .error(R.drawable.ic_broken_image)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()
    }
}