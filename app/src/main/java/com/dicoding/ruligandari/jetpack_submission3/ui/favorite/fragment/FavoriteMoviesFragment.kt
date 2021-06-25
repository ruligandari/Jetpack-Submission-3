package com.dicoding.ruligandari.jetpack_submission3.ui.favorite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ruligandari.jetpack_submission3.databinding.FragmentMoviesBinding
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.adapter.FavoriteMoviesAdapter
import com.dicoding.ruligandari.jetpack_submission3.ui.favorite.viewmodel.FavoriteMoviesViewModel
import com.dicoding.ruligandari.jetpack_submission3.util.ViewModelFactory

class FavoriteMoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[FavoriteMoviesViewModel::class.java]

        val favoriteMoviesAdapter = FavoriteMoviesAdapter(context)

        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, {movies ->
            binding.progressBar.visibility = View.GONE
            binding.llLayoutNoItem.visibility = if (movies.size == 0) View.VISIBLE else View.GONE
            favoriteMoviesAdapter.submitList(movies)
        })

        with(binding.rvMovies){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteMoviesAdapter
        }
    }

}
