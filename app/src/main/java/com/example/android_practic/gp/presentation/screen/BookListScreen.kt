package com.example.android_practic.gp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.navigation.Route
import com.example.android_practic.gp.presentation.MockData
import com.example.android_practic.BookDetails
import com.example.android_practic.gp.presentation.model.BookUiModel
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun BookListScreen(
    topLevelBackStack: TopLevelBackStack<Route>
) {
    val Book = remember { MockData.getBook() }

    LazyColumn {
        items(Book, key = { it.index }) { Book ->
            BookListItem(
                Book = Book,
                onBookClick = {
                    topLevelBackStack.add(BookDetails(Book.index))
                }
            )
        }
    }
}

@Composable
fun BookListItem(
    Book: BookUiModel,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onBookClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
    ) {
        Text(
            text = Book.Name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp)
        )

        Text(
            text = "Автор: ${Book.author}",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }


    HorizontalDivider()
}
