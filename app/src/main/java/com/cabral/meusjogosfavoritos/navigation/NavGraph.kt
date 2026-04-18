package com.cabral.meusjogosfavoritos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cabral.meusjogosfavoritos.ui.gamedetail.GameDetailScreen
import com.cabral.meusjogosfavoritos.ui.listgames.ListGamesScreen
import com.cabral.meusjogosfavoritos.ui.listgamesfavorites.ListGamesFavoriteScreen
import com.cabral.meusjogosfavoritos.ui.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit,
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Game.route,
        modifier = modifier
    ) {

        composable(Routes.Game.route) {
            ListGamesScreen(modifier, onClick = {
                navController.navigate(Routes.GameDetail.createRoute(it))
            })
        }

        composable(Routes.Favorites.route) {
            ListGamesFavoriteScreen(modifier, onClick = {
                navController.navigate(Routes.GameDetail.createRoute(it))
            })
        }
        composable(Routes.Settings.route) {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,
                currentLanguage = currentLanguage,
                onLanguageChange = onLanguageChange
            )
        }

        composable(
            route = Routes.GameDetail.route,
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("gameId") ?: 0

            GameDetailScreen()
        }
    }
}
