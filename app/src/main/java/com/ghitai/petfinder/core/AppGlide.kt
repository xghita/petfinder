package com.ghitai.petfinder.core


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ghitai.petfinder.R

object AppGlide {
    fun load(url: String?, view: ImageView) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.loading)
            .fallback(R.drawable.placeholder)
            .into(view)
    }
}