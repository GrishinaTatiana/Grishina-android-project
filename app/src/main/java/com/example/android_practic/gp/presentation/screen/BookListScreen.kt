package com.example.android_practic.gp.presentation.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.android_practic.gp.presentation.model.BookListFilter
import com.example.android_practic.uikit.Spacing




@Composable
fun BookListScreen(topLevelBackStack: TopLevelBackStack<Route>) {
    val viewModel = koinViewModel<BookListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    BookListScreenContent(
        state,
        viewModel::onBookClick,
        viewModel::onRetryClick,
        viewModel::onSettingsClick,
        viewModel::onFilterChange,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookListScreenContent(
    state: BookListViewState,
    onBookClick: (BookUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onFilterChange: (BookListFilter) -> Unit = {},
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {onSettingsClick()}) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        },
        topBar = {
            TopAppBar(
                { BookListFilter(state, onFilterChange) },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Box(Modifier.padding(it)){
            when (state.listState) {
                BookListViewState.State.Loading -> {
                    FullscreenLoading()
                }

                is BookListViewState.State.Error -> {
                    FullscreenError(
                        retry = { onRetryClick() },
                        text = state.listState.error
                    )
                }

                is BookListViewState.State.Success -> {
                    LazyColumn {
                        state.listState.data.forEach { book ->
                            item {
                                BookListItem(book) { onBookClick(it) }
                            }
                        }
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

@Composable
private fun BookListFilter(
    state: BookListViewState,
    onFilterChange: (BookListFilter) -> Unit,
){
    FlowRow (
        horizontalArrangement = Arrangement.spacedBy(Spacing.small)
    ){
        state.filters.forEach { filter ->
            FilterChip(
                selected = filter == state.currentFilter,
                label = { Text(filter.text)},
                onClick = {onFilterChange(filter)},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListPreview() {
    BookListScreenContent(
        BookListViewState(BookListViewState.State.Success(MockData.getBook()))
    )
}
