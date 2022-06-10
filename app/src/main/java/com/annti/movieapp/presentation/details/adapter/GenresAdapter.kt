package com.annti.movieapp.presentation.details.adapter

import androidx.recyclerview.widget.DiffUtil
import com.annti.movieapp.data.model.Genres
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class GenresAdapter :
    AsyncListDifferDelegationAdapter<Genres>(GenresDiffUtilCallback()) {


    init {
        delegatesManager
            .addDelegate(GenresAdapterDelegate())
    }

    class GenresDiffUtilCallback : DiffUtil.ItemCallback<Genres>() {
        override fun areItemsTheSame(
            oldItem: Genres,
            newItem: Genres
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Genres,
            newItem: Genres
        ): Boolean {
            return oldItem == newItem
        }
    }
}