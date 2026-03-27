package com.cabral.gamesrating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cabral.gamesrating.navigation.BottomNavigationBar
import com.cabral.gamesrating.navigation.NavGraph
import com.cabral.gamesrating.navigation.Routes
//import androidx.activity.viewModels
import com.cabral.gamesrating.ui.listmovies.ListMoviesScreen
import com.cabral.gamesrating.ui.theme.GamesRatingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //com o hilt é a instancia viewModels
    //private val sharedLoggedViewModel: GamesSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            GamesRatingTheme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        val bottomNavLoggedRoutes =
                            listOf(Routes.Game, Routes.Favorites)
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
                            bottom = 72.dp// if (currentRoute in bottomNavLoggedRoutes.map { it.route }) 112.dp else 0.dp
                        )

                    )
                }
            }
        }
    }
}