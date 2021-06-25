package com.dicoding.ruligandari.jetpack_submission3.ui.favorite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ruligandari.jetpack_submission3.databinding.FragmentTvShowsBinding
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.adapter.FavoriteTvShowsAdapter
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.viewmodel.FavoriteTvShowsViewModel
import com.dicoding.ruligandari.jetpack_submission3.util.ViewModelFactory

class FavoriteTvShowsFragment : Fragment(){

    private lateinit var binding: FragmentTvShowsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[FavoriteTvShowsViewModel::class.java]

        val favoriteTvShowsAdapter = FavoriteTvShowsAdapter(context)

        viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, {
            tvShows -> binding.progressBar.visibility =  View.GONE
            binding.llLayoutNoItem.visibility = if (tvShows.size == 0) View.VISIBLE else View.GONE
            favoriteTvShowsAdapter.submitList(tvShows)
        })
        with(binding.rvTvShows){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteTvShowsAdapter
        }
    }

}
