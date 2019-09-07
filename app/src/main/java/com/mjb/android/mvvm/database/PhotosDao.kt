package com.mjb.android.mvvm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface PhotosDao : BaseDao<Photo>{
    @Query("""
        SELECT * FROM photo""")
    fun getFonts(): LiveData<List<Photo>>

    @Query("DELETE FROM photo")
    fun deleteAll()
}