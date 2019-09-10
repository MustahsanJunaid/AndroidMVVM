package com.mjb.android.mvvm.util

import com.mjb.android.mvvm.database.Photo

object TestUtil{
    fun createPhoto():List<Photo>{
        return listOf(
            Photo(1,1,"thumburi","title","url"),
            Photo(2,1,"thumburi","title","url"),
            Photo(1,2,"thumburi","title","url")
        )
    }
}