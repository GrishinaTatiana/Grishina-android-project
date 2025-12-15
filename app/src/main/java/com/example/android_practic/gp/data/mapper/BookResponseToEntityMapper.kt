package com.example.android_practic.gp.data.mapper

//import com.example.android_practic.core.orNow
//import com.example.android_practic.tryParseServerDate
import com.example.android_practic.gp.data.model.BookListResponse
import com.example.android_practic.gp.domain.model.BookEntity
import com.example.android_practic.core.orNow

class BookResponseToEntityMapper {
    fun mapResponse(response: BookListResponse): List<BookEntity> {
        return response.documents?.map { doc ->
            BookEntity(
                index = doc.name.orEmpty(),
                Name = doc.fields?.Name?.stringValue.orEmpty(),
                author = doc.fields?.author?.stringValue.orNow(),
                imageUrl = doc.fields?.imageUrl?.stringValue,
                originalLanguage = doc.fields?.originalLanguage?.stringValue,
                firstPublication = doc.fields?.firstPublication?.stringValue,
            )
        }.orEmpty()
    }
}