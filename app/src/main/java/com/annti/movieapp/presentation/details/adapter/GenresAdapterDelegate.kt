package com.annti.movieapp.presentation.details.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annti.movieapp.R
import com.annti.movieapp.databinding.ItemGenreBinding
import com.annti.movieapp.data.model.Genres
import com.annti.movieapp.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class GenresAdapterDelegate :
    AbsListItemAdapterDelegate<Genres, Genres, GenresAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Genres,
        items: MutableList<Genres>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_genre))
    }

    override fun onBindViewHolder(
        item: Genres,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGenreBinding.bind(view)
        fun bind(genres: Genres) {
            binding.txtGenre.text = genres.name
        }
    }
}
