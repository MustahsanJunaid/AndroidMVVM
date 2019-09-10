package com.mjb.android.mvvm

import android.content.SharedPreferences
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mjb.android.mvvm.database.MvvmDb
import com.mjb.android.mvvm.database.Photo
import com.mjb.android.mvvm.database.PhotosDao
import com.mjb.android.mvvm.network.ApiService
import com.mjb.android.mvvm.network.Resource
import com.mjb.android.mvvm.ui.photos.PhotosRepo
import com.mjb.android.mvvm.util.ApiUtil.successCall
import com.mjb.android.mvvm.util.InstantAppExecutors
import com.mjb.android.mvvm.util.TestUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class PhotosRepoTest {
    private lateinit var photosRepo: PhotosRepo
    private val dao = mock(PhotosDao::class.java)
    private val service = mock(ApiService::class.java)
    private val prefs = mock(SharedPreferences::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(MvvmDb::class.java)
        `when`(db.photosDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        photosRepo = PhotosRepo(InstantAppExecutors(), service, prefs, dao)
    }

    @Test
    fun loadPhotos() {
        val dbData = MutableLiveData<List<Photo>>()
        `when`(dao.getPhotos()).thenReturn(dbData)

        val photo = TestUtil.createPhoto()
        val call = successCall(photo)
        `when`(service.getPhotos()).thenReturn(call)

        val data = photosRepo.getPhotos()
        verify(dao).getPhotos()
        verifyNoMoreInteractions(service)

        val observer = mock(Observer::class.java) as Observer<Resource<List<Photo>>>
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        verify(observer).onChanged(Resource.loading(null))
        val updatedDbData = MutableLiveData<List<Photo>>()
        `when`(dao.getPhotos()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(service).getPhotos()
        verify(dao).insert(photo)

        updatedDbData.postValue(photo)
        verify(observer).onChanged(Resource.success(photo))
    }
}