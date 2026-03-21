package com.cabral.gamesrating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cabral.gamesrating.navigation.NavGraph
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

                Surface {
                    NavGraph(navController = navController, modifier = Modifier)
                }
            }
            //ListMoviesScreen()
        }
    }
}