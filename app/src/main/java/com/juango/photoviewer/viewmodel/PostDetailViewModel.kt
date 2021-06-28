package com.juango.photoviewer.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.juango.photoviewer.service.model.Post
import com.juango.photoviewer.service.repository.PhotoViewerRepository
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val state: SavedStateHandle,
    private val repository: PhotoViewerRepository
) : ViewModel() {

    companion object {
        private const val POST_DETAIL_KEY = "postDetail"
    }

    private val postLiveData = MutableLiveData<Post>()

    fun loadPost(idPost: String) {
        viewModelScope.launch {
            postLiveData.postValue(repository.getPostById(idPost))
        }
    }

    fun getPostLiveData(): LiveData<Post> = postLiveData

    fun saveState() {
        state.set(POST_DETAIL_KEY, getPostLiveData().value)
    }

}

@Suppress("UNCHECKED_CAST")
class PostDetailViewModelFactory(
    private val repository: PhotoViewerRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return PostDetailViewModel(handle, repository) as T
    }
}
