package com.annti.movieapp.presentation.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.annti.movieapp.R
import com.annti.movieapp.databinding.FragmentDetailsBinding
import com.annti.movieapp.presentation.details.adapter.GenresAdapter
import com.annti.movieapp.presentation.favorite.FavoriteMovieViewModel
import com.annti.movieapp.presentation.movie.MovieViewModel
import com.annti.movieapp.utils.dateFormat
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: MovieViewModel by viewModel()
    private val favMovieViewModel: FavoriteMovieViewModel by viewModel()
    private val genresAdapter: GenresAdapter by lazy { GenresAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        bind()
        setRecyclerViewListGenres()
        addFavoriteMovie()
        deleteFavoriteMovie()
        shareMovie()

        favMovieViewModel.updateFavoriteMovie(args.movie.id)
    }

    private fun shareMovie() {
        binding.shareButton.setOnClickListener {
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plan"
                i.putExtra(Intent.EXTRA_SUBJECT, args.movie.title)
                val body: String =
                    "I advise you to watch the movie " + "\"${args.movie.title}\"" + "\n" + "I found it in the Movie App"
                i.putExtra(Intent.EXTRA_TEXT, body)
                startActivity(Intent.createChooser(i, "Share with :"))
            } catch (e: Exception) {
                Toast.makeText(context, "Sorry, can not be share", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setRecyclerViewListGenres() {
        with(binding.txtGenre) {
            adapter = genresAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
        }
    }

    private fun deleteFavoriteMovie() {
        binding.toolbar.setOnMenuItemClickListener {
            favMovieViewModel.removeFavoriteMovie(args.movie)
            true
        }
    }

    private fun bind() {
        viewModel.getMovieDetails(args.movie.id)
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            genresAdapter.items = it.genres
            binding.txtReleaseDate.text = "Release: ${dateFormat(it.release_date)}"
            binding.txtPopularity.text = it.voteAverage.toString()
        }

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/original${args.movie.posterPath}")
            .into(binding.imagePoster)

        binding.titleMovie.text = args.movie.title
        binding.txtDescriptionMovie.text = args.movie.overview
    }

    private fun addFavoriteMovie() {
        binding.buttonFavorite.setOnClickListener {
            favMovieViewModel.saveMovie(args.movie)
        }
        favMovieViewModel.heartPaintedOverButton.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) {
                binding.buttonFavorite.setImageResource(R.drawable.ic_heart_fill)
            } else binding.buttonFavorite.setImageResource(R.drawable.ic_heart)

        }
    }
}