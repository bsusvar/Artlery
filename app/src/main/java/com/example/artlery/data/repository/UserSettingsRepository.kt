package com.example.artlery.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class UserSettingsRepository(private val context: Context) {

    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val APP_THEME = stringPreferencesKey("app_theme")
    }

    val userNameFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[USER_NAME] ?: ""
    }

    val appThemeFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[APP_THEME] ?: "Sistema"
    }

    suspend fun saveUsername(name: String) {
        context.dataStore.edit { prefs -> prefs[USER_NAME] = name }
    }

    suspend fun saveTheme(theme: String) {
        context.dataStore.edit { prefs -> prefs[APP_THEME] = theme }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_NAME)
        }
    }
}