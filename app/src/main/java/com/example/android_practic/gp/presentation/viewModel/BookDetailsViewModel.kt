package com.example.android_practic.gp.presentation.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.model.BookDetailsViewState
import com.example.android_practic.gp.presentation.model.BookUiModel

class BookDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val news: BookUiModel,
): ViewModel() {
    private val mutableState = MutableStateFlow(BookDetailsViewState(news))
    val state = mutableState.asStateFlow()

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}
