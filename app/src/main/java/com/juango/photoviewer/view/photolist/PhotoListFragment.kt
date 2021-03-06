package com.juango.photoviewer.view.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.juango.photoviewer.databinding.FragmentPhotoListBinding
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.view.utils.navControllerSafeNavigate
import com.juango.photoviewer.view.viewpager.ViewPagerFragmentDirections
import com.juango.photoviewer.viewmodel.PhotoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    private val viewModel: PhotoListViewModel by viewModels()

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
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    private fun initUi() {
        binding.pullToRefresh.setOnRefreshListener { loadPhotos() }
        binding.photoRecyclerView.adapter = adapter
        binding.photoRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
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
            parentFragment?.navControllerSafeNavigate(action)
        }
    }
}