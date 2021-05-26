package com.juango.photoviewer.view.photodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.juango.photoviewer.databinding.FragmentPhotoDetailBinding
import com.juango.photoviewer.view.utils.setImageByGlide
import com.juango.photoviewer.viewmodel.PhotoDetailViewModel
import com.juango.photoviewer.viewmodel.PhotoDetailViewModelFactory

class PhotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val args: PhotoDetailFragmentArgs by navArgs()
    private lateinit var viewModel: PhotoDetailViewModel

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.loadDataFromRepository(args.idPost)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewModel()
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        viewModel.getPhotoLiveData().observe(this as LifecycleOwner, { photo ->
            photo?.let {
                binding.photoTitle.text = photo.title
                binding.photoImage.setImageByGlide(photo.url)
            }
        })
    }

    private fun initViewModel() {
        val factory = PhotoDetailViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(PhotoDetailViewModel::class.java)
    }

}