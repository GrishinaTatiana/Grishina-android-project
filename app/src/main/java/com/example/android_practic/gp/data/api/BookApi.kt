package com.example.android_practic.gp.data.api

import com.example.android_practic.gp.data.model.BookListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("documents/book")
    suspend fun getBook(
        @Query("orderBy") orderBy: String = AUTHOR_KEY,
    ): BookListResponse

    companion object {
        private const val AUTHOR_KEY = "author asc"
    }
}