package com.example.android_practic.gp.presentation.model

data class BookListViewState(
    val listState: State = State.Loading,
    val filters: List<BookListFilter> = BookListFilter.entries,
    val currentFilter: BookListFilter = BookListFilter.ALL
) {
    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Success(val data: List<BookUiModel>) : State
    }
}

enum class BookListFilter(val text: String) {
    ALL("Все"),
    FAVORITES("Избранные"),
}

