package com.example.artlery.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.artlery.data.repository.UserSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserSettingsRepository(application)

    val userName: StateFlow<String> = repository.userNameFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val appTheme: StateFlow<String> = repository.appThemeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Sistema"
    )

    fun login(name: String) {
        viewModelScope.launch { repository.saveUsername(name) }
    }

    fun logout() {
        viewModelScope.launch { repository.clearSession() }
    }

    fun updateTheme(theme: String) {
        viewModelScope.launch { repository.saveTheme(theme) }
    }
}