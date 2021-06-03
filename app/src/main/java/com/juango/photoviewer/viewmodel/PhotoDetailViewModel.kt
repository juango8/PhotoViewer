package com.juango.photoviewer.viewmodel

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.repository.PhotoViewerRepository
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    private val state: SavedStateHandle,
    private val repository: PhotoViewerRepository
) : ViewModel() {

    companion object {
        private const val PHOTO_DETAIL_KEY = "photoDetail"
        const val FILE_AUTHORITY = "com.juango.photoviewer.fileprovider"
    }

    private val photoLiveData = MutableLiveData<Photo>()

    fun loadDataFromRepository(idPost: Int) {
        viewModelScope.launch {
            photoLiveData.postValue(repository.getPhotoById(idPost.toString()))
        }
    }

    fun getPhotoLiveData(): LiveData<Photo> =
        if (state.contains(PHOTO_DETAIL_KEY)) {
            state.getLiveData(PHOTO_DETAIL_KEY)
        } else
            photoLiveData

    fun saveState() {
        state.set(PHOTO_DETAIL_KEY, getPhotoLiveData().value)
    }

    suspend fun createImageOnCache(): Uri {
        return repository.saveImageInCache(photoLiveData.value!!)
    }
}

@Suppress("UNCHECKED_CAST")
class PhotoDetailViewModelFactory(
    private val repository: PhotoViewerRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return PhotoDetailViewModel(handle, repository) as T
    }
}