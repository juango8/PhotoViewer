package com.juango.photoviewer.ui.photodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.juango.photoviewer.App
import com.juango.photoviewer.databinding.FragmentPhotoDetailBinding
import com.juango.photoviewer.utils.setImageByGlide
import kotlinx.coroutines.launch

class PhotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val args: PhotoDetailFragmentArgs by navArgs()
    private val repository by lazy { App.repository }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        loadImage()
        binding.photoTitle.text = args.idPost.toString()
    }

    private fun loadImage() {
        lifecycleScope.launch {
            val photo = repository.getPhotoById(args.idPost.toString())
            binding.photoTitle.text = photo.title
            binding.photoImage.setImageByGlide(photo.url)
        }
    }
}