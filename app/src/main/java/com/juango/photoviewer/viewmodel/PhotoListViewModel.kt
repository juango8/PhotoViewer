package com.juango.photoviewer.viewmodel

import androidx.lifecycle.*
import com.juango.photoviewer.App
import com.juango.photoviewer.service.model.relations.PhotoAndAlbum
import kotlinx.coroutines.launch

class PhotoListViewModel(private val state: SavedStateHandle) : ViewModel() {

    companion object {
        private const val PHOTO_LIST_KEY = "photoList"
    }

    private val repository by lazy { App.repository }

    private val photoListLiveData = MutableLiveData<List<PhotoAndAlbum>>()

    fun loadPhotoListFromRepository(albumId: String) {
        viewModelScope.launch {
            photoListLiveData.postValue(repository.getPhotosByAlbum(albumId))
        }
    }

    fun getPhotoListLiveData(): LiveData<List<PhotoAndAlbum>> =
        if (state.contains(PHOTO_LIST_KEY)) {
            state.getLiveData(PHOTO_LIST_KEY)
        } else
            photoListLiveData


    fun saveState() {
        state.set(PHOTO_LIST_KEY, getPhotoListLiveData().value)
    }
}