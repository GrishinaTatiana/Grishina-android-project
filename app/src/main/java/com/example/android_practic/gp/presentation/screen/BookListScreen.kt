package com.example.android_practic.gp.presentation.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_practic.Book
import com.example.android_practic.BookDetails
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.MockData
import com.example.android_practic.gp.presentation.model.BookUiModel
import com.example.android_practic.gp.presentation.viewModel.BookListViewModel
import com.example.android_practic.uikit.FullscreenError
import com.example.android_practic.uikit.FullscreenLoading
import com.example.android_practic.gp.presentation.model.BookListViewState
import org.koin.androidx.compose.koinViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue

@Composable
fun BookListScreen(topLevelBackStack: TopLevelBackStack<Route>) {
    val viewModel = koinViewModel<BookListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    BookListScreenContent(
        state.state,
        viewModel::onBookClick,
        viewModel::onRetryClick,
    )
}

@Composable
private fun BookListScreenContent(
    state: BookListViewState.State,
    onBookClick: (BookUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
) {
    when (state) {
        BookListViewState.State.Loading -> {
            FullscreenLoading()
        }

        is BookListViewState.State.Error -> {
            FullscreenError(
                retry = { onRetryClick() },
                text = state.error
            )
        }

        is BookListViewState.State.Success -> {
            LazyColumn {
                state.data.forEach { book ->
                    item {
                        BookListItem(book) { onBookClick(it) }
                    }
                }
            }
        }
    }
}

@Composable
fun BookListItem(book: BookUiModel, onBookClick: (BookUiModel) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onBookClick(book) }
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = book.author,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = book.Name,
            style = MaterialTheme.typography.titleMedium,
        )

        if (!book.originalLanguage.isNullOrBlank()) {
            Text(
                text = "Язык оригинала - ${book.originalLanguage}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
fun BookListPreview() {
    BookListScreen(TopLevelBackStack<Route>(Book))
}
