package com.ergea.ridsnewsapp.common

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ergea.ridsnewsapp.R
import com.google.android.material.snackbar.Snackbar

typealias Rdrawable = R.drawable

fun View.showSnackBar(text: String) =
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
        .show()

fun View.show(visible: Boolean) {
    this.isVisible = visible
}

fun ImageView.loadImage(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(Rdrawable.no_image)
        .error(Rdrawable.no_image)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}