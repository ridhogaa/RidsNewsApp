package com.ergea.ridsnewsapp.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ergea.ridsnewsapp.R
import com.google.android.material.snackbar.Snackbar
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


typealias Rdrawable = R.drawable

fun View.showSnackBar(text: String) =
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
        .show()

fun View.show(visible: Boolean) {
    this.isVisible = visible
}

fun ImageView.loadImage(context: Context, url: String?, progressBar: ProgressBar?) {
    Glide.with(context)
        .load(url)
        .placeholder(Rdrawable.no_image)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.show(false);
                return false;
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.show(false)
                return false;
            }
        })
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(Rdrawable.no_image)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun dateFormat(oldStringDate: String?): String? {
    val newDate: String?
    val dateFormat = SimpleDateFormat("E, d MMM yyyy", Locale(getCountry()))
    newDate = try {
        val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldStringDate)
        dateFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
    return newDate
}

fun dateToTimeFormat(oldstringDate: String?): String? {
    val p = PrettyTime(getCountry()?.let { Locale(it) })
    var isTime: String? = ""
    try {
        val sdf = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.getDefault()
        )
        val date = oldstringDate?.let { sdf.parse(it) }
        isTime = p.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return isTime
}

fun getCountry(): String? {
    val locale = Locale.getDefault()
    val country = locale.country.toString()
    return country.lowercase(Locale.getDefault())
}

