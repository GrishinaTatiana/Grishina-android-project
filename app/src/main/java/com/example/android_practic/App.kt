package com.example.android_practic



import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import com.example.android_practic.di.mainModule
import com.example.android_practic.di.networkModule
import com.example.android_practic.di.BookFeatureModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainModule, networkModule, BookFeatureModule)
        }
    }
}