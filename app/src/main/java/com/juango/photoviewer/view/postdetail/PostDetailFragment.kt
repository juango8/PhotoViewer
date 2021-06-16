package com.juango.photoviewer.view.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.juango.photoviewer.App
import com.juango.photoviewer.databinding.FragmentPostDetailBinding
import com.juango.photoviewer.viewmodel.PostDetailViewModel
import com.juango.photoviewer.viewmodel.PostDetailViewModelFactory

class PostDetailFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailBinding
    private val args: PostDetailFragmentArgs by navArgs()
    private lateinit var viewModel: PostDetailViewModel

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.loadPost(args.idPost)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewModel()
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
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
        viewModel.getPostLiveData().observe(this as LifecycleOwner, { post ->
            post?.let {
                binding.tvTitleDetail.text = post.title
                binding.tvContentDetail.text = post.body
            }
        })
    }

    private fun initViewModel() {
        val factory = PostDetailViewModelFactory(App.repository, this)
        viewModel = ViewModelProvider(this, factory).get(PostDetailViewModel::class.java)
    }

}