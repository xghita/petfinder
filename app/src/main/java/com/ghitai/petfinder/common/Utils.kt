package com.ghitai.petfinder.common

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibleIfNotNull")
fun setVisibleIfNotNull(view: View, value: Double?) {
    view.visibility = if (value != null && value > 0.0) View.VISIBLE else View.GONE
}