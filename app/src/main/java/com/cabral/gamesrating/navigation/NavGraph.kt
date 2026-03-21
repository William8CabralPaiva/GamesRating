package com.cabral.gamesrating.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cabral.gamesrating.ui.gamedetail.GameDetailScreen
import com.cabral.gamesrating.ui.listmovies.ListMoviesScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Game.route,
        modifier = modifier
    ) {

        composable(Routes.Game.route) {
            ListMoviesScreen(modifier, onClick = {
                navController.navigate(Routes.GameDetail.createRoute(it))
            })
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