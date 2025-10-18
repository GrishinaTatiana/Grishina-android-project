package com.example.android_practic

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.android_practic.ui.theme.Android_practicTheme
import androidx.activity.ComponentActivity

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_practicTheme {
                MainScreen()
            }
        }
    }
}