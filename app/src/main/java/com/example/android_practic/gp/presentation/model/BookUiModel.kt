package com.example.android_practic.gp.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class BookUiModel(
    val index: String,
    val Name: String,
    val author: String,
    val originalLanguage: String?,
    val firstPublication: String?,
    val imageUrl: String?,
)
