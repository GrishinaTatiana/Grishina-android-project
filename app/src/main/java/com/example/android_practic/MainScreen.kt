package com.example.android_practic


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import org.koin.java.KoinJavaComponent.inject
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.model.BookUiModel
import com.example.android_practic.gp.presentation.screen.BookDetailsDialog
import com.example.android_practic.gp.presentation.screen.BookListScreen
import kotlin.getValue

interface TopLevelRoute: Route {
    val icon: ImageVector
}
data object Info: TopLevelRoute {
    override val icon = Icons.Default.Info
}

data object Book: TopLevelRoute {
    override val icon = Icons.AutoMirrored.Filled.List
}

data class BookDetails(val book: BookUiModel) : Route

@Composable
fun MainScreen() {
    val topLevelBackStack by inject<TopLevelBackStack<Route>>(clazz = TopLevelBackStack::class.java)
    val dialogStrategy = remember { DialogSceneStrategy<Route>() }

    Scaffold(bottomBar = {
        NavigationBar {
            listOf(Info, Book).forEach { route ->
                NavigationBarItem(
                    icon = { Icon(route.icon, null) },
                    selected = topLevelBackStack.topLevelKey == route,
                    onClick = {
                        topLevelBackStack.addTopLevel(route)
                    }
                )
            }
        }
    }) { padding ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            modifier = Modifier.padding(padding),
            sceneStrategy = dialogStrategy,
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Info> {
                    PlaceholderScreen("Скоро здесь будет дополнительная информация")
                }
                entry<Book> {
                    BookListScreen(topLevelBackStack)
                }
                entry<BookDetails>(
                    metadata = DialogSceneStrategy.dialog(DialogProperties())
                ) {
                    BookDetailsDialog(it.book)
                }
            }
        )
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(text = text, color = Color.Black)
    }
}


