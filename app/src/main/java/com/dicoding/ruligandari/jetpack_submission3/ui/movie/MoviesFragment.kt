package com.dicoding.ruligandari.jetpack_submission3.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ruligandari.jetpack_submission3.databinding.FragmentMoviesBinding
import com.dicoding.ruligandari.jetpack_submission3.util.ViewModelFactory
import com.dicoding.ruligandari.jetpack_submission3.vo.Status

class MoviesFragment: Fragment() {
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

        val movieAdapter = MovieAdapter(context)

        viewModel.getMovies().observe(viewLifecycleOwner, {
            movies -> if (movies != null){
                when(movies.status){
                    Status.LOADING -> binding.progressBar.visibility = View.GONE

                    Status.SUCCESS -> {
                        binding.llLayoutNoItem.visibility =
                            if (movies.data?.size == 0) View.VISIBLE else View.GONE
                        movieAdapter.submitList(movies.data)
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        val text = "Terjadi Kesalahan"
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(context, text, duration)
                        toast.show()
                    }
                }

        }
        })
        with(binding.rvMovies){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
}