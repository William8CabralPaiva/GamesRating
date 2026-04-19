package com.cabral.meusjogosfavoritos.ui.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = SurfaceLight,
    secondary = SecondaryDark,
    onSecondary = SurfaceLight,
    background = BackgroundDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    tertiary = Orange,
    onTertiary = OnSurfaceLight
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = SurfaceLight,
    secondary = SecondaryLight,
    onSecondary = OnSurfaceLight,
    background = BackgroundLight,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    tertiary = Orange,
    onTertiary = OnSurfaceLight
)

@Composable
fun GamesRatingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    SideEffect {
        val activity = view.context as? ComponentActivity ?: return@SideEffect
        val barColor = colorScheme.background.toArgb()

        activity.enableEdgeToEdge(
            statusBarStyle = if (darkTheme) {
                // No tema escuro, fundo escuro e ícones claros
                SystemBarStyle.dark(barColor)
            } else {
                // No tema claro, fundo claro e ícones escuros
                SystemBarStyle.light(barColor, barColor)
            },
            navigationBarStyle = if (darkTheme) {
                SystemBarStyle.dark(barColor)
            } else {
                SystemBarStyle.light(barColor, barColor)
            }
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
