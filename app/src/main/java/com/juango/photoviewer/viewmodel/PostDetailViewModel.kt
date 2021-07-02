package com.juango.photoviewer.viewmodel

import androidx.lifecycle.*
import com.juango.photoviewer.service.model.Post
import com.juango.photoviewer.service.repository.PhotoViewerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
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