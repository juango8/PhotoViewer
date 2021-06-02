package com.juango.photoviewer.view.photodetail

import android.content.Intent
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
        initListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::viewModel.isInitialized)
            viewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    private fun initUi() {
        viewModel.getPhotoLiveData().observe(this as LifecycleOwner, { photo ->
            photo?.let {
                binding.photoTitle.text = photo.title
                binding.photoImage.setImageByGlide(photo.url)
            }
        })
    }

    private fun initListeners() {
        binding.imageButton.setOnClickListener {
            shareImage()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(PhotoDetailViewModel::class.java)
    }

    private fun shareImage() {
        val uri = viewModel.createImageOnCache()
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Image selected.")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "image/*"
        startActivity(Intent.createChooser(shareIntent, "Share..."))
    }
}