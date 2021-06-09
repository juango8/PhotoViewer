package com.juango.photoviewer.view.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juango.photoviewer.service.Preferences

abstract class MyThemeAppCompatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getThemeFromPrefs(Preferences.getTheme(this)))
        super.onCreate(savedInstanceState)
    }
}