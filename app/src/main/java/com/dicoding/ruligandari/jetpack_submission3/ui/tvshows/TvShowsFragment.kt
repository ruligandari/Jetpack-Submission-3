package com.dicoding.ruligandari.jetpack_submission3.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ruligandari.jetpack_submission3.databinding.FragmentTvShowsBinding
import com.dicoding.ruligandari.jetpack_submission3.util.ViewModelFactory
import com.dicoding.ruligandari.jetpack_submission3.vo.Status

class TvShowsFragment : Fragment(){

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

        val factory =  ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[TvShowsViewModel::class.java]
        val tvShowsAdapter = TvShowsAdapter(context)

        viewModel.getTvShows().observe(viewLifecycleOwner, {
            tvShows -> if (tvShows != null){
                when(tvShows.status){
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        binding.llLayoutNoItem.visibility =
                            if (tvShows.data?.size == 0) View.VISIBLE else View.GONE
                        tvShowsAdapter.submitList(tvShows.data)
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
        with(binding.rvTvShows){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }
    }

}