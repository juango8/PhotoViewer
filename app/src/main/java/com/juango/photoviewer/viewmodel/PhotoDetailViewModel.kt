package com.juango.photoviewer.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import com.juango.photoviewer.App
import com.juango.photoviewer.service.model.Photo
import kotlinx.coroutines.launch

class PhotoDetailViewModel(private val state: SavedStateHandle) : ViewModel() {

    companion object {
        private const val PHOTO_DETAIL_KEY = "photoDetail"
        const val FILE_AUTHORITY = "com.juango.photoviewer.fileprovider"
    }

    private val repository by lazy { App.repository }

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