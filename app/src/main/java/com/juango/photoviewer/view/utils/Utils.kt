package com.juango.photoviewer.view.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.juango.photoviewer.R

fun ImageView.setImageByGlide(url: String) {
    val userAgent =
        "Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.181 Mobile Safari/537.36"

    val glideUrl = GlideUrl(
        url,
        LazyHeaders.Builder().addHeader("User-Agent", userAgent).build()
    )

    val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

    Glide.with(context)
        .applyDefaultRequestOptions(requestOptions)
        .load(glideUrl)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .timeout(60000)
        .override(320, 480)
        .into(this)
}

fun getBitmapFromGlideURL(src: String, context: Context): Bitmap {
    var imageFromServer: Bitmap? = null
    Glide
        .with(context)
        .asBitmap()
        .load(src)
        .into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                imageFromServer = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })
    return imageFromServer ?: BitmapFactory.decodeResource(
        context.resources,
        R.drawable.ic_android_red
    )
}