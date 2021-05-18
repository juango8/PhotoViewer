package com.juango.photoviewer.ui.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.juango.photoviewer.databinding.FragmentPhotoListBinding
import com.juango.photoviewer.model.Photo

class PhotoListFragment : Fragment() {

    private lateinit var binding: FragmentPhotoListBinding
    private val adapter by lazy { PhotoListAdapter(::onItemSelected) }
//    private val repository by lazy { App.repository }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        binding.photoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onItemSelected(item: Photo) {
        view?.let {
            val action =
                PhotoListFragmentDirections.actionPhotoListFragmentToPhotoDetailFragment(item.id)
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun loadPhotos() {
        val photos = listOf(
            Photo(
                1,
                1,
                "accusamus beatae ad facilis cum similique qui sunt",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952"
            ),
            Photo(
                1,
                2,
                "reprehenderit est deserunt velit ipsam",
                "https://via.placeholder.com/600/771796",
                "https://via.placeholder.com/150/771796"
            )
        )
        adapter.setData(photos)
        binding.pullToRefresh.isRefreshing = false
    }

}