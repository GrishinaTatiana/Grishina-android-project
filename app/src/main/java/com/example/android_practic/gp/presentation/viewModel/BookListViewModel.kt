package com.example.android_practic.gp.presentation.viewModel

import android.widget.Filter
import androidx.lifecycle.ViewModel
import com.example.android_practic.BookDetails
import com.example.android_practic.gp.presentation.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.model.BookListViewState
import com.example.android_practic.gp.presentation.model.BookUiModel
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.android_practic.BookSettings

//import com.example.android_practic.core.formatDateTime
import com.example.android_practic.core.launchLoadingAndError
import com.example.android_practic.gp.domain.interactor.BookInteractor
import com.example.android_practic.gp.domain.model.BookEntity
import com.example.android_practic.gp.presentation.model.BookListFilter
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.flow.map



class BookListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: BookInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(BookListViewState())
    val viewState = mutableState.asStateFlow()

    init {
        loadBook()
    }

    fun onBookClick(book: BookUiModel) {
        topLevelBackStack.add(BookDetails(book))
    }

    fun onRetryClick() = loadBook()

    fun onSettingsClick() = topLevelBackStack.add(BookSettings)

    fun onFilterChange(filter: BookListFilter) {
        mutableState.update { it.copy(currentFilter = filter) }
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launchLoadingAndError(
            handleError = { e ->
                updateState(BookListViewState.State.Error(e.localizedMessage.orEmpty()))
            }
        ) {
            updateState(BookListViewState.State.Loading)

            interactor.observeNewFirstSettings()
                .onEach { updateState(BookListViewState.State.Loading) }
                .map {
                    if (viewState.value.currentFilter == BookListFilter.ALL) {
                        interactor .getBook(it)
                    } else{
                        interactor.getFavorite()
                    }

                }
                .collect { book ->
                    updateState(BookListViewState.State.Success(mapToUi(book)))
                }


        }
    }

    private fun updateState(state: BookListViewState.State) =
        mutableState.update { it.copy(listState = state) }

    private fun mapToUi(book: List<BookEntity>): List<BookUiModel> = book.map { book ->
        BookUiModel(
            index = book.index,
            Name = book.Name,
            author = book.author,
            imageUrl = book.imageUrl,
            originalLanguage = book.originalLanguage,
            firstPublication = book.firstPublication,
        )
    }
}

