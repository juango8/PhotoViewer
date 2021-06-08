package com.juango.photoviewer.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.juango.photoviewer.R
import com.juango.photoviewer.databinding.ActivityPrefsBinding
import com.juango.photoviewer.service.Preferences
import com.juango.photoviewer.view.utils.getThemeFromPrefs


class PrefsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrefsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getThemeFromPrefs(Preferences.getTheme(this)))
        super.onCreate(savedInstanceState)
        binding = ActivityPrefsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initToolbar()
        initListeners()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initListeners() {
        binding.imageViewDefaultTheme.setOnClickListener {
            Toast.makeText(this, "Theme changed", Toast.LENGTH_SHORT).show()
            Preferences.setTheme(this, 0)
        }
        binding.imageViewChillTheme.setOnClickListener {
            Toast.makeText(this, "Theme changed", Toast.LENGTH_SHORT).show()
            Preferences.setTheme(this, 1)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@PrefsActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}