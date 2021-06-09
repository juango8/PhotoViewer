package com.juango.photoviewer.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.juango.photoviewer.service.model.relations.PhotoAndAlbum
import com.juango.photoviewer.service.repository.PhotoViewerRepository
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val state: SavedStateHandle,
    private val repository: PhotoViewerRepository
) : ViewModel() {

    companion object {
        private const val PHOTO_LIST_KEY = "photoList"
    }

    private val photoListLiveData = MutableLiveData<List<PhotoAndAlbum>>()

    init {
        if (state.contains(PHOTO_LIST_KEY)) {
            photoListLiveData.value =
                state.getLiveData<List<PhotoAndAlbum>>(PHOTO_LIST_KEY).value
        }
    }

    fun loadPhotoList(albumId: String) {
        viewModelScope.launch {
            photoListLiveData.postValue(repository.getPhotosByAlbum(albumId))
        }
    }

    fun getPhotoListLiveData(): LiveData<List<PhotoAndAlbum>> =
        photoListLiveData


    fun saveState() {
        state.set(PHOTO_LIST_KEY, getPhotoListLiveData().value)
    }
}

@Suppress("UNCHECKED_CAST")
class PhotoListViewModelFactory(
    private val repository: PhotoViewerRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return PhotoListViewModel(handle, repository) as T
    }
}