package com.example.android_practic.gp.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
class BookListResponse(
    val documents: List<BookListDocument>?,
)

@Keep
@Serializable
class BookListDocument(
    val name: String? = null,
    val fields: BookFirestoreModel?,
)

@Keep
@Serializable
class BookFirestoreModel(
    //val index: NumberFirestoreModel,
    val Name: StringFirestoreModel?,
    val author: StringFirestoreModel?,
    val originalLanguage: StringFirestoreModel?,
    val firstPublication: StringFirestoreModel?,
    val imageUrl: StringFirestoreModel?,
)