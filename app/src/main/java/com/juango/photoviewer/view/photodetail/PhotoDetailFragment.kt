package com.juango.photoviewer.view.photodetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.juango.photoviewer.databinding.FragmentPhotoDetailBinding
import com.juango.photoviewer.view.utils.setImageByGlide
import com.juango.photoviewer.viewmodel.PhotoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val args: PhotoDetailFragmentArgs by navArgs()
    private val viewModel: PhotoDetailViewModel by viewModels()

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.loadData(args.idPhoto)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
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

    @DelicateCoroutinesApi
    private fun initListeners() {
        binding.imageButton.setOnClickListener {
            shareImage()
        }
    }

    @DelicateCoroutinesApi
    private fun shareImage() {
        lifecycleScope.launch {
            val uri = context?.let { viewModel.createImageOnCache(it) }
            uri.let {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Image selected.")
                shareIntent.putExtra(Intent.EXTRA_STREAM, it)
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                shareIntent.type = "image/*"
                startActivity(Intent.createChooser(shareIntent, "Share..."))
            }
        }

    }

}