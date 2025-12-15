package com.example.android_practic.gp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.android_practic.gp.data.api.BookApi
import com.example.android_practic.gp.data.mapper.BookResponseToEntityMapper
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.android_practic.gp.data.db.BookDatabase
import com.example.android_practic.gp.domain.model.BookEntity
import kotlin.String
import androidx.room.Dao
import androidx.room.Query
import com.example.android_practic.gp.data.entity.BookDbEntity


class BookRepository(
    private val api: BookApi,
    private val mapper: BookResponseToEntityMapper,
    private val dataStore: DataStore<Preferences>,
    private  val db: BookDatabase,
) {
    private val newFirstKey = booleanPreferencesKey(NEW_FIRST_KEY)
    suspend fun getBook(newFirst: Boolean) = withContext(Dispatchers.IO) {
        val response = api.getBook(orderBy = "$CREATE_TIME_KEY ${if (newFirst) CREATE_TIME_ASC else CREATE_TIME_DESC}")
        mapper.mapResponse(response)
    }

    suspend fun setNewFirstSettings(newFirst: Boolean) = withContext(Dispatchers.IO){
        dataStore.edit {
            it[newFirstKey] = newFirst
        }
    }

    fun observeNewFirstSettings(): Flow<Boolean> =
        dataStore.data.map { it[newFirstKey] ?: false }

    suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            db.bookDao().getAll().map {
                BookEntity(
                    index = it.id.toString(),
                    Name = it.Name.orEmpty(),
                    author = it.author.orEmpty(),
                    originalLanguage = it.originalLanguage.orEmpty(),
                    firstPublication = it.firstPublication.orEmpty(),
                    imageUrl = null,
                )
            }
        }

    suspend fun saveFavorites(book: BookEntity) =
        withContext(Dispatchers.IO) {
            db.bookDao().insent(
                BookDbEntity(
                    Name = book.Name,
                    author = book.author,
                    originalLanguage = book.originalLanguage,
                    firstPublication = book.firstPublication,
                )
            )
        }

    companion object{
        private const val NEW_FIRST_KEY = "NEW_FIRST_KEY"
        private const val CREATE_TIME_KEY = "author"
        private const val CREATE_TIME_ASC = "asc"
        private const val CREATE_TIME_DESC = "desc"
    }
}













