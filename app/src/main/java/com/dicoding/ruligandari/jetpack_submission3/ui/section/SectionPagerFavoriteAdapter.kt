package com.dicoding.ruligandari.jetpack_submission3.ui.section

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.ruligandari.jetpack_submission3.R
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.fragment.FavoriteMoviesFragment
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.fragment.FavoriteTvShowsFragment

class SectionPagerFavoriteAdapter(private val mContext: Context, fm: FragmentManager):
FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_movie, R.string.tab_tv_show)
    }

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when (position){
            0 -> fragment = FavoriteMoviesFragment()
            1 -> fragment = FavoriteTvShowsFragment()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? =
        mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = TAB_TITLES.size
}