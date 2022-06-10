package com.annti.movieapp.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.annti.movieapp.R
import com.annti.movieapp.databinding.FragmentMovieBinding
import com.annti.movieapp.data.model.Results
import com.annti.movieapp.presentation.movie.adapter.MovieAdapter
import com.annti.movieapp.presentation.movie.adapter.MovieAdapterDelegate
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapterDelegate.ItemSelected {

    private val viewModel: MovieViewModel by viewModel()
    private lateinit var binding: FragmentMovieBinding
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.error.observe(viewLifecycleOwner) {
            binding.txtError.text = it
        }
        viewModel.errorView.observe(viewLifecycleOwner) { error ->
            if (error) {
                binding.frameAlarmError.visibility = View.VISIBLE
            } else binding.frameAlarmError.visibility = View.GONE
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else binding.progressBar.visibility = View.GONE
        }

        getMovieList()
        setRecyclerViewListMovie()
        search()
    }


    private fun setRecyclerViewListMovie() {
        with(binding.listMovie) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    private fun getMovieList() {
        viewModel.discoverMovie()
        viewModel.getMovieList()
        viewModel.movieList.observe(viewLifecycleOwner) {
            movieAdapter.items = it
        }
    }

    private fun search() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                viewModel.searchMovie(query!!)
                binding.searchView.setQuery("", false)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        viewModel.errorView.observe(viewLifecycleOwner) { error ->
            if (error) {
                binding.imageView2.setImageResource(R.drawable.ic_big_search)
                binding.frameAlarmError.visibility = View.VISIBLE
                binding.listMovie.visibility = View.GONE
            } else {
                binding.frameAlarmError.visibility = View.GONE
                binding.listMovie.visibility = View.VISIBLE
            }
        }
    }

    override fun onItemSelected(item: Results) {
        val action = MovieFragmentDirections.actionMovieFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }

}