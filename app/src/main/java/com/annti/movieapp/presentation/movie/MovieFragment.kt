package com.annti.movieapp.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.annti.movieapp.R
import com.annti.movieapp.databinding.FragmentMovieBinding
import com.annti.movieapp.data.model.Results
import com.annti.movieapp.presentation.details.DetailsFragmentArgs
import com.annti.movieapp.presentation.favorite.FavoriteMovieViewModel
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

        getMovieList()
        setRecyclerViewListMovie()
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
        viewModel.errorView.observe(viewLifecycleOwner) { error ->
            showError(error)
        }
        viewModel.loading.observe(viewLifecycleOwner) { progress ->
            showProgress(progress)
        }
        viewModel.error.observe(viewLifecycleOwner){ errorText ->
            binding.frameAlarmError.setText(errorText)
        }
    }

    private fun showProgress(progress: Boolean) {
        binding.progressBar.isVisible = progress
        binding.listMovie.isVisible = !progress
    }

    //TODO разобраться с видимостью списка
    private fun showError(show: Boolean) {
        binding.listMovie.isVisible = !show
        binding.frameAlarmError.isVisible = show
    }

    override fun onItemSelected(item: Results) {
        val action = MovieFragmentDirections.actionMovieFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }
}