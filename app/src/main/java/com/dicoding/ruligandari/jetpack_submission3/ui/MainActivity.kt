package com.dicoding.ruligandari.jetpack_submission3.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ruligandari.jetpack_submission3.databinding.ActivityMainBinding
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.FavoriteActivity
import com.dicoding.ruligandari.jetpack_submission3.ui.section.SectionPagerMainAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mFragmentManager = supportFragmentManager
        val sectionsPagerAdapter =
            SectionPagerMainAdapter(this, mFragmentManager)

        with(binding) {
            viewpager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(binding.viewpager)
            ivFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra:id"
    }
}