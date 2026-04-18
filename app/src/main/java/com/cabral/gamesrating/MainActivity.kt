package com.cabral.gamesrating

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cabral.gamesrating.navigation.BottomNavigationBar
import com.cabral.gamesrating.navigation.NavGraph
import com.cabral.gamesrating.navigation.Routes
import com.cabral.gamesrating.ui.theme.GamesRatingTheme
import com.cabral.gamesrating.util.SettingsManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isDarkTheme by remember { mutableStateOf(settingsManager.isDarkTheme()) }
            
            // Obtém o idioma atual inicial do manager
            var currentLanguage by remember {
                mutableStateOf(settingsManager.getLanguage())
            }

            // Aplica o idioma ao AppCompatDelegate se for diferente do atual do sistema na primeira vez
            remember {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(currentLanguage)
                AppCompatDelegate.setApplicationLocales(appLocale)
                true
            }

            GamesRatingTheme(isDarkTheme) {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        val bottomNavLoggedRoutes =
                            listOf(Routes.Game, Routes.Favorites, Routes.Settings)
                        val currentBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = currentBackStackEntry?.destination?.route

                        if (currentRoute in bottomNavLoggedRoutes.map { it.route }) {
                            BottomNavigationBar(
                                navController = navController,
                                loggedRoutes = bottomNavLoggedRoutes
                            )
                        }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = 72.dp
                        ),
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { 
                            isDarkTheme = it 
                            settingsManager.setDarkTheme(it)
                        },
                        currentLanguage = currentLanguage,
                        onLanguageChange = { languageCode ->
                            currentLanguage = languageCode
                            settingsManager.setLanguage(languageCode)
                            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
                            AppCompatDelegate.setApplicationLocales(appLocale)
                        }
                    )
                }
            }
        }
    }
}
