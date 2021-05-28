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

    init {
        loadPhotoListFromRepository()
    }

    private fun loadPhotoListFromRepository() {
        viewModelScope.launch {
            photoListLiveData.postValue(repository.getPhotos())
        }
    }

    fun getPhotoListLiveData(): LiveData<List<Photo>> =
        if (state.contains(PHOTO_LIST_KEY)) {
            state.getLiveData(PHOTO_LIST_KEY)
        } else
            photoListLiveData


    fun saveState() {
        state.set(PHOTO_LIST_KEY, getPhotoListLiveData().value)
    }
}