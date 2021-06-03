package com.juango.photoviewer.view.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.juango.photoviewer.App
import com.juango.photoviewer.databinding.FragmentPhotoListBinding
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.view.viewpager.ViewPagerFragmentDirections
import com.juango.photoviewer.viewmodel.PhotoListViewModel
import com.juango.photoviewer.viewmodel.PhotoListViewModelFactory

class PhotoListFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val photoListFragment = PhotoListFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            photoListFragment.arguments = bundle
            return photoListFragment
        }
    }


    private lateinit var binding: FragmentPhotoListBinding
    private val adapter by lazy { PhotoListAdapter(::onItemSelected) }
    private lateinit var viewModel: PhotoListViewModel

    init {
        lifecycleScope.launchWhenStarted {
            val albumId = requireArguments().getInt(ARG_POSITION) + 1
            viewModel.loadPhotoList(albumId.toString())
            loadPhotos()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewModel()
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::viewModel.isInitialized)
            viewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    private fun initUi() {
        binding.pullToRefresh.setOnRefreshListener { loadPhotos() }
        binding.photoRecyclerView.adapter = adapter
        binding.photoRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
    }

    private fun initViewModel() {
        val factory = PhotoListViewModelFactory(App.repository, this)
        viewModel = ViewModelProvider(this, factory).get(PhotoListViewModel::class.java)
    }

    private fun loadPhotos() {
        binding.pullToRefresh.isRefreshing = true

        viewModel.getPhotoListLiveData().observe(this as LifecycleOwner, { photoList ->
            photoList?.let {
                adapter.setData(photoList)
            }
        })

        binding.pullToRefresh.isRefreshing = false
    }

    private fun onItemSelected(item: Photo) {
        view?.let {
            val action =
                ViewPagerFragmentDirections.actionPhotoListFragmentToPhotoDetailFragment(item.id)
            NavHostFragment.findNavController(this).navigate(action)
        }
    }
}