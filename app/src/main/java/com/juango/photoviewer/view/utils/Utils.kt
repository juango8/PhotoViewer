package com.juango.photoviewer.view.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.juango.photoviewer.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


const val userAgent =
    "Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.181 Mobile Safari/537.36"
val headers: LazyHeaders = LazyHeaders.Builder().addHeader("User-Agent", userAgent).build()


fun ImageView.setImageByGlide(url: String) {
    val glideUrl = GlideUrl(url, headers)

    val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

    Glide.with(context)
        .applyDefaultRequestOptions(requestOptions)
        .load(glideUrl)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .timeout(60000)
        .override(320, 480)
        .into(this)
}

suspend fun getBitmapFromGlideURL(url: String, context: Context): Bitmap =
    withContext(Dispatchers.IO) {
        val glideUrl = GlideUrl(url, headers)
        var imageFromServer: Bitmap? = null

        try {
            imageFromServer = Glide
                .with(context)
                .asBitmap()
                .load(glideUrl)
                .submit()
                .get(6000, TimeUnit.MILLISECONDS)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return@withContext imageFromServer ?: BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_android_red
        )
    }