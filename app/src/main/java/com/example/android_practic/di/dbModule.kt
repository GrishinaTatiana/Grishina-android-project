package com.example.android_practic.di

import android.content.Context
import androidx.room.Room
import com.example.android_practic.gp.data.db.BookDatabase
import org.koin.dsl.module

val dbModule = module {
    single { DatabaseBuilder.getInstance(get()) }
}

object DatabaseBuilder {


    fun getInstance(context: Context) = buildRoomDB(context)
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            BookDatabase::class.java,
            "book"
        ).build()











