package com.mjb.android.mvvm.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Photo(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)