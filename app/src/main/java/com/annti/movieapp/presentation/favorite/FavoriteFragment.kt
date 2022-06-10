package com.annti.movieapp.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.annti.movieapp.R
import com.annti.movieapp.data.model.Results
import com.annti.movieapp.databinding.FragmentFavoriteBinding
import com.annti.movieapp.presentation.favorite.adapter.FavMovieAdapterDelegate
import com.annti.movieapp.presentation.favorite.adapter.FavoriteMovieAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect

class FavoriteFragment : Fragment(R.layout.fragment_favorite),
    FavMovieAdapterDelegate.ItemSelected {

    private val viewModel: FavoriteMovieViewModel by viewModel()
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteMovieAdapter: FavoriteMovieAdapter by lazy { FavoriteMovieAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewListMovie()
        getFavoriteMovieList()
        deleteAllFavoritesMovies(view)
    }

    private fun setRecyclerViewListMovie() {
        with(binding.favoriteListMovie) {
            adapter = favoriteMovieAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(false)
        }
    }

    private fun deleteAllFavoritesMovies(view: View){
        binding.toolbar.setOnMenuItemClickListener {
            Snackbar.make(
                view,
                "Do you really want to delete all yours favorite movies?",
                Snackbar.LENGTH_LONG
            )
                .setAction("Yes") {
                    viewModel.removeAllFavoritesMovie()
                }
                .show()
            true
        }
    }

    private fun getFavoriteMovieList() {
        viewModel.getFavorites()
        lifecycleScope.launchWhenCreated {
            viewModel.movies.collect {
                favoriteMovieAdapter.items = it
                if (it.isNullOrEmpty()){
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = "Empty list"
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            binding.errorText.text = it
        }
        viewModel.errorView.observe(viewLifecycleOwner) { error ->
            if (error) {
                binding.errorText.visibility = View.VISIBLE
            } else {
                binding.errorText.visibility = View.GONE
            }
        }
    }

    override fun onItemSelected(item: Results) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(item)
        findNavController().navigate(action)
    }
}