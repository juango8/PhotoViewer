package com.juango.photoviewer.view.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
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

fun getThemeFromPrefs(themeId: Int): Int {
    return when (themeId) {
        0 -> R.style.Theme_PhotoViewer
        1 -> R.style.Theme_PhotoViewer1
        else -> R.style.Theme_PhotoViewer
    }
}

fun Fragment.navControllerSafeNavigate(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val navController = findNavController()
    val currentDestinationClassName = when (val destination = navController.currentDestination) {
        is FragmentNavigator.Destination -> destination.className
        is DialogFragmentNavigator.Destination -> destination.className
        else -> return
    }
    if (parentFragment?.javaClass?.name == currentDestinationClassName || javaClass.name == currentDestinationClassName)
        navController.navigate(resId, args, navOptions, navigatorExtras)
}

fun Fragment.navControllerSafeNavigate(
    directions: NavDirections,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navControllerSafeNavigate(
        directions.actionId,
        directions.arguments,
        navOptions,
        navigatorExtras
    )
}