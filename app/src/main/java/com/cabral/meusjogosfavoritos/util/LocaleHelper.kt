package com.cabral.meusjogosfavoritos.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

object LocaleHelper {

    fun applyLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        
        val localizedContext = context.createConfigurationContext(configuration)
        
        // Retornamos um ContextWrapper para que o Hilt consiga encontrar a Activity original
        // percorrendo o baseContext, mas sobrescrevemos o getResources para usar o localizado.
        return object : ContextWrapper(context) {
            override fun getResources(): Resources {
                return localizedContext.resources
            }
        }
    }
}
