package com.cabral.gamesrating.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cabral.gamesrating.ui.gamedetail.GameDetailScreen
import com.cabral.gamesrating.ui.listgames.ListGamesScreen
import com.cabral.gamesrating.ui.listgamesfavorites.ListGamesFavoriteScreen
import com.cabral.gamesrating.ui.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
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
                onToggleTheme = onToggleTheme
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