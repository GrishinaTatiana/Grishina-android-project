package com.example.android_practic.gp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android_practic.gp.data.entity.BookDbEntity

@Dao
interface BookDao {
    @Query("SELECT * FROM BookDbEntity")
    suspend fun getAll(): List<BookDbEntity>

    @Insert
    suspend fun insent(driverDbEntity: BookDbEntity)

}