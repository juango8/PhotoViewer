package com.juango.photoviewer.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.juango.photoviewer.view.photolist.PhotoListFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val items: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return items
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoListFragment.getInstance(position)
    }
}