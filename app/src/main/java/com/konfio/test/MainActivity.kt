package com.konfio.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.konfio.test.feature.dogsscreen.ui.ListDogScreen
import com.konfio.test.ui.theme.KonfioTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KonfioTestTheme {
                ListDogScreen()
            }
        }
    }
}