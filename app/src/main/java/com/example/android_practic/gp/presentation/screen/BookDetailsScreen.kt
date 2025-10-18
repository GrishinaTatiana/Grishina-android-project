package com.example.android_practic.gp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import com.example.android_practic.gp.presentation.model.BookUiModel
import com.example.android_practic.gp.presentation.model.BookDetailsViewState
import com.example.android_practic.gp.presentation.viewModel.BookDetailsViewModel
import com.example.android_practic.uikit.RatingBar
import androidx.compose.foundation.layout.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    Book: BookUiModel,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<BookDetailsViewModel>(
        key = "BookDetails_${Book.index}"
    ) {
        parametersOf(Book.index)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(state.Book.Name) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val context = LocalContext.current
                    IconButton(onClick = {
                        shareText(context, state.Book.Name)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { padding ->
        BookDetailsContent(
            state = state,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            onRatingChanged = viewModel::onRatingChanged,
            onFavoriteToggled = viewModel::onFavoriteToggled
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookDetailsContent(
    state: BookDetailsViewState,
    modifier: Modifier = Modifier,
    onRatingChanged: (Float) -> Unit = {},
    onFavoriteToggled: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (avatar, name, infoColumn, ratingBar) = createRefs()

        GlideImage(
            model = state.Book.imageUrl,
            contentDescription = "Avatar",
            modifier = Modifier
                .width(80.dp)
                .height(150.dp)
                .constrainAs(avatar) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = state.Book.Name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(avatar.top)
                start.linkTo(avatar.end, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.constrainAs(infoColumn) {
                top.linkTo(avatar.bottom, margin = 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
        ) {
            if (!state.Book.Name.isNullOrBlank()) {
                Text("Название: ${state.Book.Name}")
            }
            if (!state.Book.author.isNullOrBlank()) {
                Text("Автор: ${state.Book.author}")
            }
            if (!state.Book.originalLanguage.isNullOrBlank()) {
                Text("Язык оригинала: ${state.Book.originalLanguage}")
            }
            if (!state.Book.firstPublication.isNullOrBlank()) {
                Text("Первая публикация: ${state.Book.firstPublication}")
            }
            if (state.Book.genres.isNotEmpty()) {
                Text("Жанры: ${state.Book.genres.joinToString()}")
            }
        }


    }
}