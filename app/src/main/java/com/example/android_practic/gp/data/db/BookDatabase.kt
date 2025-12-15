package com.example.android_practic.gp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_practic.gp.data.dao.BookDao
import com.example.android_practic.gp.data.entity.BookDbEntity

@Database(entities = [BookDbEntity::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao() : BookDao
}