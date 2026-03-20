package com.cabral.gamesrating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.cabral.gamesrating.ui.GamesSharedViewModel
import com.cabral.gamesrating.ui.ListMoviesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //com o hilt é a instancia viewModels
    private val sharedLoggedViewModel: GamesSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            ListMoviesScreen(sharedViewModel = sharedLoggedViewModel)
        }
    }
}