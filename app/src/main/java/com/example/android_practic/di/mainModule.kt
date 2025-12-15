package com.example.android_practic.di

import android.content.Context
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.example.android_practic.Book
import com.example.android_practic.navigation.Route
import com.example.android_practic.navigation.TopLevelBackStack
import com.example.android_practic.gp.presentation.viewModel.BookDetailsViewModel
import com.example.android_practic.gp.presentation.viewModel.BookListViewModel
//import java.util.prefs.Preferences


import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import org.koin.android.ext.koin.androidContext


val mainModule = module {
    single { TopLevelBackStack<Route>(Book) }
    single {
        getDataStore(androidContext())
    }
}

fun getDataStore(androidContext: Context): DataStore<Preferences> =
    PreferenceDataStoreFactory.create {
        androidContext.preferencesDataStoreFile("default")
    }