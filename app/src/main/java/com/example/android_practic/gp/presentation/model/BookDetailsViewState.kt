package com.example.android_practic.gp.presentation.model

data class BookDetailsViewState(
    val Book: BookUiModel,
    val rating: Float = 0f,
    val isFavorite: Boolean = false
) {
    val userVoteVisible get() = rating != 0f
}