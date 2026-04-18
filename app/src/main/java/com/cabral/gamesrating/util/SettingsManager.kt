package com.cabral.gamesrating.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    fun isDarkTheme(): Boolean {
        return prefs.getBoolean("dark_theme", false)
    }

    fun setDarkTheme(enabled: Boolean) {
        prefs.edit().putBoolean("dark_theme", enabled).apply()
    }

    // Para o idioma, embora o AppCompatDelegate salve, podemos querer ler para o estado inicial do Compose
    fun getLanguage(): String {
        return prefs.getString("app_language", "pt") ?: "pt"
    }

    fun setLanguage(language: String) {
        prefs.edit().putString("app_language", language).apply()
    }
}
