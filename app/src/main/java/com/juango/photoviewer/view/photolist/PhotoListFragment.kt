package com.juango.photoviewer.view.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.juango.photoviewer.App
import com.juango.photoviewer.databinding.FragmentPhotoListBinding
import com.juango.photoviewer.service.model.Photo
import kotlinx.coroutines.launch

class PhotoListFragment : Fragment() {

    private lateinit var binding: FragmentPhotoListBinding
    private val adapter by lazy { PhotoListAdapter(::onItemSelected) }
    private val repository by lazy { App.repository }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onStart() {
        super.onStart()
        loadPhotos()
    }

    private fun initUi() {
        binding.pullToRefresh.setOnRefreshListener { loadPhotos() }
        binding.photoRecyclerView.adapter = adapter
        binding.photoRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
    }

    private fun onItemSelected(item: Photo) {
        view?.let {
            val action =
                PhotoListFragmentDirections.actionPhotoListFragmentToPhotoDetailFragment(item.id)
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun loadPhotos() = lifecycleScope.launch {
        binding.pullToRefresh.isRefreshing = true

        val photos = repository.getPhotos()
        adapter.setData(photos)

        binding.pullToRefresh.isRefreshing = false
    }
}