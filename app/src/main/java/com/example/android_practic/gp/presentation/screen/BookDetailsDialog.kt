package com.example.android_practic.gp.presentation.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.MockData
import com.example.android_practic.gp.presentation.model.BookDetailsViewState
import com.example.android_practic.gp.presentation.model.BookUiModel
import com.example.android_practic.gp.presentation.viewModel.BookDetailsViewModel
import com.example.android_practic.uikit.RatingBar
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsDialog(
    book: BookUiModel,
) {
    val viewModel = koinViewModel<BookDetailsViewModel> {
        parametersOf(book)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = { viewModel.onBack() },
    ) {
        BookDetailsContent(state, viewModel::onRatingChanged)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookDetailsContent(
    state: BookDetailsViewState,
    onRatingChanged: (Float) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val context = LocalContext.current

        Icon(
            Icons.Default.Share,
            null,
            Modifier.clickable {
                shareText(context, state.book.Name)
            }
        )

        Text(
            text = state.book.author,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "'${state.book.Name}'",
            style = MaterialTheme.typography.titleMedium,
        )

        if (!state.book.originalLanguage.isNullOrBlank()) {
            Text(
                text = "Язык оригинала - ${state.book.originalLanguage}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (!state.book.firstPublication.isNullOrBlank()) {
            Text(
                text = "Первая публикация - ${state.book.firstPublication}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }


        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            GlideImage(
                model = state.book.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(150.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit,
            )
        }

        RatingBar(state.rating) { onRatingChanged(it) }

        if (state.userVoteVisible) {
            Text("Ваша оценка: ${state.rating}")
        }
    }
}

fun shareText(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Поделиться через"))
}

@Preview(showBackground = true)
@Composable
fun BookDetailDialogPreview() {
    BookDetailsContent(
        BookDetailsViewState(MockData.getBook().first()),
    )
}