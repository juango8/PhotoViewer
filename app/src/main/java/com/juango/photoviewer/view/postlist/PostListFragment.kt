package com.juango.photoviewer.view.postlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.juango.photoviewer.databinding.FragmentPostListBinding
import com.juango.photoviewer.service.model.Post
import com.juango.photoviewer.view.utils.navControllerSafeNavigate
import com.juango.photoviewer.viewmodel.PostListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : Fragment() {

    private lateinit var binding: FragmentPostListBinding
    private val adapter by lazy { PostListAdapter(::onItemSelected) }
    private val viewModel: PostListViewModel by viewModels()

    init {
        lifecycleScope.launchWhenStarted {
            loadPost()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostListBinding.inflate(inflater, container, false)
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
        binding.pullToRefresh.setOnRefreshListener { loadPost() }
        binding.postRecyclerView.adapter = adapter
        binding.postRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadPost() {
        binding.pullToRefresh.isRefreshing = true

        viewModel.getPostListLiveData().observe(this as LifecycleOwner, { postList ->
            postList?.let {
                adapter.setData(postList)
            }
        })

        binding.pullToRefresh.isRefreshing = false
    }

    private fun onItemSelected(item: Post) {
        view?.let {
            val action =
                PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(item.id.toString())
            navControllerSafeNavigate(action)
        }
    }

}