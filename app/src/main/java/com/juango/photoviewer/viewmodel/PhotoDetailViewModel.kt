package com.juango.photoviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juango.photoviewer.App
import com.juango.photoviewer.service.model.Photo
import kotlinx.coroutines.launch

class PhotoDetailViewModel : ViewModel() {

    private val repository by lazy { App.repository }

    private val photoLiveData = MutableLiveData<Photo>()

    fun getPhotoLiveData(): LiveData<Photo> = photoLiveData

    fun loadDataFromRepository(idPost: Int) {
        viewModelScope.launch {
            photoLiveData.postValue(repository.getPhotoById(idPost.toString()))
        }
    }
}