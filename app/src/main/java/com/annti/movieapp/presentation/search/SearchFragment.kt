package com.annti.movieapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.annti.movieapp.R
import com.annti.movieapp.data.model.Results
import com.annti.movieapp.databinding.FragmentSearchBinding
import com.annti.movieapp.presentation.movie.MovieFragmentDirections
import com.annti.movieapp.presentation.movie.adapter.MovieAdapter
import com.annti.movieapp.presentation.movie.adapter.MovieAdapterDelegate
import com.annti.movieapp.utils.onQueryTextChange
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search), MovieAdapterDelegate.ItemSelected {

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding
    private val searchMovieAdapter: MovieAdapter by lazy { MovieAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search()
        setRecyclerViewListMovie()
    }

    private fun setRecyclerViewListMovie() {
        with(binding.listMovieSearch) {
            adapter = searchMovieAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    private fun search() {
        val titleMovie = binding.searchView.onQueryTextChange()
        viewModel.searchMovie(titleMovie)

        lifecycleScope.launchWhenCreated {
            viewModel.movieList.collect {
                searchMovieAdapter.items = it
                if (it.isNullOrEmpty()) {
                    binding.frameAlarmError.setImage(R.drawable.ic_big_search)
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            showProgress(it)
        }
        viewModel.error.observe(viewLifecycleOwner) { errorText ->
            binding.frameAlarmError.setText(errorText)
        }
        viewModel.errorView.observe(viewLifecycleOwner) { error ->
            binding.frameAlarmError.setImage(R.drawable.ic_alarm_error)
            showError(error)
        }
    }

    private fun showProgress(show: Boolean) {
        binding.progressBar.isVisible = show
        binding.listMovieSearch.isVisible = !show
    }

    private fun showError(show: Boolean) {
        binding.listMovieSearch.isVisible = !show
        binding.frameAlarmError.isVisible = show
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelJob()
    }

    override fun onItemSelected(item: Results) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }

}