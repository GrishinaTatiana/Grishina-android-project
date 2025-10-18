package com.example.android_practic.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.navigation.Route
import com.example.android_practic.gp.presentation.MockData
import com.example.android_practic.gp.presentation.model.BookDetailsViewState

class BookDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    BookIndex: Int
) : ViewModel() {

    private val Book = MockData.getBook().first { it.index == BookIndex }

    private val mutableState = MutableStateFlow(BookDetailsViewState(Book))
    val state = mutableState.asStateFlow()

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }
    }

    fun onFavoriteToggled() {
        mutableState.update { it.copy(isFavorite = !it.isFavorite) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}