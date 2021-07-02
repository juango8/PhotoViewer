package com.juango.photoviewer.viewmodel

import androidx.lifecycle.*
import com.juango.photoviewer.service.model.relations.PhotoAndAlbum
import com.juango.photoviewer.service.repository.PhotoViewerRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: PhotoViewerRepositoryImpl
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