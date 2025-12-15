package com.example.android_practic.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practic.gp.domain.interactor.BookInteractor
import com.example.android_practic.gp.domain.model.BookEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.model.BookDetailsViewState
import com.example.android_practic.gp.presentation.model.BookUiModel
import kotlinx.coroutines.launch


class BookDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val book: BookUiModel,
    private val interactor: BookInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(BookDetailsViewState(book))
    val state = mutableState.asStateFlow()

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }
        if (rating > 4f) {
            viewModelScope.launch {
                interactor.saveFavorite(
                    BookEntity(
                        book.index,
                        book.Name,
                        book.author,
                        book.originalLanguage,
                        book.firstPublication,
                        book.imageUrl
                    )
                )
            }
        }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}
