package com.example.android_practic.gp.presentation.model


data class BookDetailsViewState(
    val book: BookUiModel,
    val rating: Float = 0f,
) {
    val userVoteVisible get() = rating != 0f
}

