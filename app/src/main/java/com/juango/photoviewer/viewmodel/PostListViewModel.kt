package com.juango.photoviewer.viewmodel

import androidx.lifecycle.*
import com.juango.photoviewer.service.model.Post
import com.juango.photoviewer.service.repository.PhotoViewerRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: PhotoViewerRepositoryImpl
) : ViewModel() {

    companion object {
        private const val POST_LIST_KEY = "postList"
    }

    private val postListLiveData = MutableLiveData<List<Post>>()

    init {
        if (state.contains(POST_LIST_KEY)) {
            postListLiveData.value = state.getLiveData<List<Post>>(POST_LIST_KEY).value
        } else {
            loadPostList()
        }
    }

    private fun loadPostList() {
        viewModelScope.launch {
            postListLiveData.postValue(repository.getPosts())
        }
    }

    fun getPostListLiveData(): LiveData<List<Post>> = postListLiveData

    fun saveState() {
        state.set(POST_LIST_KEY, getPostListLiveData().value)
    }

}