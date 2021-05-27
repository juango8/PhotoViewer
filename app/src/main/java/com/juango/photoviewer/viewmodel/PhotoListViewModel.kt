package com.juango.photoviewer.viewmodel

import androidx.lifecycle.*
import com.juango.photoviewer.App
import com.juango.photoviewer.service.model.Photo
import kotlinx.coroutines.launch

class PhotoListViewModel(private val state: SavedStateHandle) : ViewModel() {

    companion object {
        private const val PHOTO_LIST_KEY = "photoList"
    }

    private val repository by lazy { App.repository }

    private val photoListLiveData = MutableLiveData<List<Photo>>()

    val photoListStateLiveData = state.getLiveData(PHOTO_LIST_KEY, photoListLiveData)

    init {
        loadPhotoListFromRepository()
    }

    fun getPhotoListLiveData(): LiveData<List<Photo>> = photoListLiveData

    private fun loadPhotoListFromRepository() {
        viewModelScope.launch {
            photoListLiveData.postValue(repository.getPhotos())
        }
    }

    fun saveState() {
        state.set(PHOTO_LIST_KEY, photoListStateLiveData.value)
    }
}