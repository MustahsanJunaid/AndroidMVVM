package com.mjb.android.mvvm.database

import androidx.room.*
import com.mjb.android.mvvm.OpenForTesting


@Dao
@OpenForTesting
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: T)

    @Update
    fun update(entity: T)

    @Delete
    fun delete(entity: T)

}