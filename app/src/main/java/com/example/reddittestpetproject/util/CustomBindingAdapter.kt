package com.example.reddittestpetproject.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*


/**
 * Apply new functionality to setImageUrl with Glide library
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .circleCrop()
        .into(imageView)
}


/**
 * Convert created_utc value from network to normal Date
 */
fun Long.toUTCDate(): String {
    return Date(this * 1000).toString()
}