package com.annti.movieapp.presentation.favorite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.annti.movieapp.data.model.Results
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class FavoriteMovieAdapter (itemSelected: FavMovieAdapterDelegate.ItemSelected) :
    AsyncListDifferDelegationAdapter<Results>(FavoriteMovieDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(FavMovieAdapterDelegate(itemSelected))
    }

    class FavoriteMovieDiffUtilCallback : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(
            oldItem: Results,
            newItem: Results
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Results,
            newItem: Results
        ): Boolean {
            return oldItem == newItem
        }
    }


}