package com.mjb.android.mvvm.network

import androidx.lifecycle.LiveData
import com.mjb.android.mvvm.database.Photo
import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    fun getPhotos(): LiveData<ApiResponse<List<Photo>>>

}