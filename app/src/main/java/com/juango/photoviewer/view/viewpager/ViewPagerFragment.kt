package com.juango.photoviewer.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.juango.photoviewer.R
import com.juango.photoviewer.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    binding.viewPager.setCurrentItem(0, false)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_playlist -> {
                    binding.viewPager.setCurrentItem(1, false)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    private var onNavigationPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> binding.navigation.menu.findItem(R.id.navigation_home).isChecked = true
                1 -> binding.navigation.menu.findItem(R.id.navigation_playlist).isChecked = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val adapter = ViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle,
            2
        )

        binding.viewPager.adapter = adapter
        binding.navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        binding.viewPager.registerOnPageChangeCallback(onNavigationPageChangeCallback)

        return binding.root

    }

}