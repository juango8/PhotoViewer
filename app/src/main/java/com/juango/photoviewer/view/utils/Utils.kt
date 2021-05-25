package com.juango.photoviewer.view.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
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
        .timeout(60000)
        .override(320, 480)
        .into(this)
}