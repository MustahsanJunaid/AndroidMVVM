package com.mjb.android.mvvm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mjb.android.mvvm.OpenForTesting

@Dao
@OpenForTesting
interface PhotosDao : BaseDao<Photo>{
    @Query("""
        SELECT * FROM photo
        ORDER BY albumId ASC""")
    fun getPhotos(): LiveData<List<Photo>>

}