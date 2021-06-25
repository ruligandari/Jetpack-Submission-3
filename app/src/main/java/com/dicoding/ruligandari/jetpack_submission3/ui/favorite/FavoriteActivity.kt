package com.dicoding.ruligandari.jetpack_submission3.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ruligandari.jetpack_submission3.databinding.ActivityFavoriteBinding
import com.dicoding.ruligandari.jetpack_submission3.ui.section.SectionPagerFavoriteAdapter

class FavoriteActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val sectionPagerAdapter = SectionPagerFavoriteAdapter(this, mFragmentManager)

        with(binding){
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(binding.viewPager)
        }
    }


}
