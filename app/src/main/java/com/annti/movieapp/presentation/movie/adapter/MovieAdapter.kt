package com.annti.movieapp.presentation.movie.adapter

import androidx.recyclerview.widget.DiffUtil
import com.annti.movieapp.data.model.Results
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieAdapter(itemSelected: MovieAdapterDelegate.ItemSelected) :
    AsyncListDifferDelegationAdapter<Results>(MovieDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(MovieAdapterDelegate(itemSelected))
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Results>() {
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