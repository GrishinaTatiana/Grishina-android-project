package com.example.android_practic.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.example.android_practic.Book
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.viewModel.BookDetailsViewModel

val mainModule = module {
    single { TopLevelBackStack<Route>(Book) }

    viewModel { BookDetailsViewModel(get(), get()) }
}
