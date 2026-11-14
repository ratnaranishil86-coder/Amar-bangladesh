package com.example.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.local.AmarBangladeshDatabase
import com.example.data.preferences.UserPreferencesStore
import com.example.data.repository.DiscoveryRepository
import com.example.data.repository.LocalDiscoveryRepository

class AppContainer(context: Context) {
  private val database: AmarBangladeshDatabase by lazy {
    AmarBangladeshDatabase.getInstance(context)
  }

  private val preferencesStore: UserPreferencesStore by lazy {
    UserPreferencesStore(context.applicationContext)
  }

  val discoveryRepository: DiscoveryRepository by lazy {
    LocalDiscoveryRepository(
      divisionDao = database.divisionDao(),
      bookmarkDao = database.bookmarkDao(),
      preferencesStore = preferencesStore,
    )
  }
}

inline fun <reified VM : ViewModel> viewModelFactory(
  crossinline creator: () -> VM
): ViewModelProvider.Factory =
  object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return creator() as T
    }
  }
