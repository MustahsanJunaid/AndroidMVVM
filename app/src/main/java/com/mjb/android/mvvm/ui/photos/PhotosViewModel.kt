package com.mjb.android.mvvm.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mjb.android.mvvm.database.Photo
import com.mjb.android.mvvm.network.Resource
import javax.inject.Inject

class PhotosViewModel
@Inject constructor(private val photosRepo: PhotosRepo) : ViewModel() {
    fun getPhotos(): LiveData<Resource<List<Photo>>> {
        return photosRepo.getPhotos()
    }
}