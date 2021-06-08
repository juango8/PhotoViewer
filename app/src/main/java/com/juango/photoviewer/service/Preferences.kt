package com.juango.photoviewer.service

import android.content.Context

object Preferences {

    private const val PREFS = "ThemePreferences"
    private const val KEY_THEME_ID = "themeId"

    fun setTheme(context: Context, themeId: Int) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_THEME_ID, themeId).apply()
    }

    fun getTheme(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_THEME_ID, 0)
    }

}