package com.mjb.android.mvvm.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mjb.android.mvvm.ktx.setThumb


// Binding Adapters on app scope
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("thumbUrl")
    fun setImageThumbUrl(imageView: ImageView, thumbUrl: String?) {
        thumbUrl?.let {
            imageView.setThumb(it)
        }
    }
}