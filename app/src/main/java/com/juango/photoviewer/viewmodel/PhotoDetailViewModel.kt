package com.juango.photoviewer.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import com.juango.photoviewer.service.model.Photo
import com.juango.photoviewer.service.repository.PhotoViewerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject
constructor(
    private val state: SavedStateHandle,
    private val repository: PhotoViewerRepository
) : ViewModel() {

    companion object {
        private const val PHOTO_DETAIL_KEY = "photoDetail"
        const val FILE_AUTHORITY = "com.juango.photoviewer.fileprovider"
    }

    private val photoLiveData = MutableLiveData<Photo>()

    init {
        if (state.contains(PHOTO_DETAIL_KEY)) {
            photoLiveData.value = state.getLiveData<Photo>(PHOTO_DETAIL_KEY).value
        }
    }

    fun loadData(idPhoto: Int) {
        viewModelScope.launch {
            photoLiveData.postValue(repository.getPhotoById(idPhoto.toString()))
        }
    }

    fun getPhotoLiveData(): LiveData<Photo> = photoLiveData

    fun saveState() {
        state.set(PHOTO_DETAIL_KEY, getPhotoLiveData().value)
    }

    suspend fun createImageOnCache(): Uri {
        return repository.saveImageInCache(photoLiveData.value!!)
    }
}