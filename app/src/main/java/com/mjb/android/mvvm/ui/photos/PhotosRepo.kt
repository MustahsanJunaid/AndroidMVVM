package com.mjb.android.mvvm.ui.photos

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.mjb.android.mvvm.OpenForTesting
import com.mjb.android.mvvm.database.Photo
import com.mjb.android.mvvm.database.PhotosDao
import com.mjb.android.mvvm.network.*
import com.mjb.android.mvvm.util.AppExecutors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OpenForTesting
class PhotosRepo
@Inject constructor(
    private val appExecutors: AppExecutors,
    private val apiService: ApiService,
    private val preferences: SharedPreferences,
    private val photosDao: PhotosDao
) {

    val key = "PhotosRepo"
    private val repoListRateLimit = RateLimiter<String>(timeout = 15, timeUnit = TimeUnit.DAYS, pref = preferences)

    fun getPhotos(): LiveData<Resource<List<Photo>>> {
        return object : NetworkBoundResource<List<Photo>, List<Photo>>(appExecutors) {


            override fun saveCallResult(item: List<Photo>) {
//                Log.v(key,"saveCallResult: $item")
                photosDao.insert(item)
            }

            override fun shouldFetch(data: List<Photo>?): Boolean {
                val shouldFetch = data == null || data.isEmpty() || repoListRateLimit.shouldFetch(key)
//                Log.v(key,"shouldFetch: $data")
//                Log.v(key,"shouldFetch: $shouldFetch")
                return shouldFetch
            }

            override fun loadFromDb(): LiveData<List<Photo>> {
                val data = photosDao.getPhotos()
//                Log.v(key,"loadFromDb")
                return data
            }

            override fun createCall(): LiveData<ApiResponse<List<Photo>>> {
                return apiService.getPhotos()
            }
        }.asLiveData()
    }

}