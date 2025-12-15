package com.example.android_practic.gp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BookDbEntity (
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "Name") val Name: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "originalLanguage") val originalLanguage: String?,
    @ColumnInfo(name = "firstPublication") val firstPublication: String?,
)