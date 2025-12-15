package com.example.android_practic.di

import com.example.android_practic.gp.presentation.viewModel.BookDetailsViewModel
import com.example.android_practic.gp.presentation.viewModel.BookListViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import retrofit2.Retrofit
import com.example.android_practic.gp.data.api.BookApi
import com.example.android_practic.gp.data.mapper.BookResponseToEntityMapper
import com.example.android_practic.gp.data.repository.BookRepository
import com.example.android_practic.gp.domain.interactor.BookInteractor
import com.example.android_practic.gp.presentation.viewModel.BookSettingsViewModel


var BookFeatureModule = module {
    single { get<Retrofit>().create(BookApi::class.java) }

    factory { BookResponseToEntityMapper() }
    single { BookRepository(get(), get(), get(), get()) }

    single { BookInteractor(get()) }


    viewModel { BookDetailsViewModel(get(), get(), get()) }
    viewModel { BookListViewModel(get(), get()) }
    viewModel { BookSettingsViewModel(get(), get()) }
}

