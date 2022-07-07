package com.annti.movieapp.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.annti.movieapp.databinding.ViewErrorBinding

class ErrorView
    @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    private val binding = ViewErrorBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setText(text: String){
        binding.txtError.text = text
    }

    fun setImage(image: Int){
        binding.imageView.setImageResource(image)
    }
}