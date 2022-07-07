package com.annti.movieapp.presentation.movie.adapter

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annti.movieapp.R
import com.annti.movieapp.data.model.GenreName
import com.annti.movieapp.databinding.ItemMovieBinding
import com.annti.movieapp.data.model.Results
import com.annti.movieapp.utils.dateFormat
import com.annti.movieapp.utils.inflate
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MovieAdapterDelegate(private val itemSelected: ItemSelected) :
    AbsListItemAdapterDelegate<Results, Results, MovieAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Results,
        items: MutableList<Results>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_movie), itemSelected)
    }

    override fun onBindViewHolder(
        item: Results,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        private val view: View,
        private val itemSelected: ItemSelected
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieBinding.bind(view)

        fun bind(movie: Results) {
            view.setOnClickListener {
                itemSelected.onItemSelected(movie)
            }
            binding.txtTitleMovie.text = movie.title
            binding.txtPopularity.text = movie.voteAverage.toString()
            binding.txtReleaseDate.text = "${dateFormat(movie.releaseDate)}"
            Glide.with(itemView)
                .load(
                    Uri.parse("https://image.tmdb.org/t/p/original${movie.posterPath}")
                )
                .placeholder(R.drawable.movie_placeholder)
                .into(binding.imagePosterMovie)

            val genreList = movie.genreIds.map { id ->
                GenreName.values().find {
                    it.genre_id == id
                }
            }
            binding.txtGenreMovie.text = genreList.joinToString()
        }
    }

    interface ItemSelected {
        fun onItemSelected(item: Results)
    }
}
