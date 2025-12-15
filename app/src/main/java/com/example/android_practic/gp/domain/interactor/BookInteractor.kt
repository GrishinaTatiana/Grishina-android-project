package com.example.android_practic.gp.domain.interactor

import com.example.android_practic.gp.data.repository.BookRepository
import com.example.android_practic.gp.domain.model.BookEntity

class BookInteractor(
    private val repository: BookRepository
) {
    suspend fun getBook(newFirst: Boolean) = repository.getBook(newFirst)

    fun observeNewFirstSettings() = repository.observeNewFirstSettings()

    suspend fun setNewFirstSettings(newFirst: Boolean) =
        repository.setNewFirstSettings(newFirst)

    suspend fun saveFavorite(book: BookEntity) = repository.saveFavorites(book)

    suspend fun getFavorite() = repository.getFavorites()

}



