package com.example.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.model.DisplayLanguage
import com.example.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userPrefsDataStore: DataStore<Preferences> by
  preferencesDataStore(name = "amar_bangladesh_prefs")

class UserPreferencesStore(private val context: Context) {
  private object Keys {
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val DISPLAY_LANGUAGE = stringPreferencesKey("display_language")
  }

  val themeMode: Flow<ThemeMode> =
    context.userPrefsDataStore.data.map { prefs ->
      val raw = prefs[Keys.THEME_MODE]
      ThemeMode.entries.firstOrNull { it.name == raw } ?: ThemeMode.SYSTEM
    }

  val displayLanguage: Flow<DisplayLanguage> =
    context.userPrefsDataStore.data.map { prefs ->
      val raw = prefs[Keys.DISPLAY_LANGUAGE]
      DisplayLanguage.entries.firstOrNull { it.name == raw } ?: DisplayLanguage.BILINGUAL
    }

  suspend fun setThemeMode(mode: ThemeMode) {
    context.userPrefsDataStore.edit { prefs -> prefs[Keys.THEME_MODE] = mode.name }
  }

  suspend fun setDisplayLanguage(language: DisplayLanguage) {
    context.userPrefsDataStore.edit { prefs -> prefs[Keys.DISPLAY_LANGUAGE] = language.name }
  }
}
