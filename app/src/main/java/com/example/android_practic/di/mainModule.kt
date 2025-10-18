package com.example.android_practic.di

import org.koin.dsl.module
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.viewModel.BookDetailsViewModel
import com.example.android_practic.Books

val mainModule = module {
    single { TopLevelBackStack<Route>(Books) }

    factory { (characterId: Int) ->
        BookDetailsViewModel(get<TopLevelBackStack<Route>>(), characterId)
    }
}