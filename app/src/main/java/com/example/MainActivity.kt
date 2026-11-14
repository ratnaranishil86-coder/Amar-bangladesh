package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.di.AppContainer
import com.example.ui.AmarBangladeshApp

class MainActivity : ComponentActivity() {
  private val appContainer by lazy { AppContainer(applicationContext) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent { AmarBangladeshApp(appContainer = appContainer) }
  }
}
