package com.mjb.android.mvvm.ktx


import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.mjb.android.mvvm.R

fun ImageView.setThumb(path: String, placeholder: Int = R.drawable.image_placeholder) {
    setThumb(path.toUri(), placeholder)
}

fun ImageView.setThumb(uri: Uri, placeholder: Int = R.drawable.image_placeholder) {
    Glide
        .with(this)
        .load(uri)
        .centerCrop()
        .placeholder(placeholder)
        .into(this)
}
