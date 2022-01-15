package com.alif.rijksmuseum.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.alif.rijksmuseum.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (imgUrl.isNullOrEmpty()) {
        Glide.with(imgView.context)
            .load(R.drawable.ic_no_data)
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_error))
            .into(imgView)
    } else {
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_error))
            .into(imgView)
    }
}

@BindingAdapter("errorMessage")
fun errorMessage(view: TextInputLayout, errorMessageResId: Int?) {
    if (errorMessageResId != null && errorMessageResId != 0) {
        view.error = view.context.getString(errorMessageResId)
    } else {
        view.error = null
    }
}

@BindingAdapter("errorCheckBox")
fun errorMessage(view: TextView, errorMessageResId: Int?) {
    if (errorMessageResId != null && errorMessageResId != 0) {
        view.error = view.context.getString(errorMessageResId)
    } else {
        view.error = null
    }
}