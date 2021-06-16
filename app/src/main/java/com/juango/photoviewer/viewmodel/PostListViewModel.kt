package com.juango.photoviewer.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.juango.photoviewer.service.model.Post
import com.juango.photoviewer.service.repository.PhotoViewerRepository
import kotlinx.coroutines.launch

class PostListViewModel(
    private val state: SavedStateHandle,
    private val repository: PhotoViewerRepository
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

    fun loadPostList() {
        viewModelScope.launch {
            postListLiveData.postValue(repository.getPosts())
        }
    }

    fun getPostListLiveData(): LiveData<List<Post>> = postListLiveData

    fun saveState() {
        state.set(POST_LIST_KEY, getPostListLiveData().value)
    }

}

@Suppress("UNCHECKED_CAST")
class PostListViewModelFactory(
    private val repository: PhotoViewerRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return PostListViewModel(handle, repository) as T
    }
}
