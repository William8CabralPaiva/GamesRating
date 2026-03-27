package com.cabral.gamesrating.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

const val ELEVATION = 8F

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    loggedRoutes: List<Routes>,
    modifier: Modifier = Modifier,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .systemBarsPadding()
            .fillMaxWidth()
            .height(64.dp) // Altura fixa ajuda na centralização vertical dos ícones
            .graphicsLayer {
                shape = RoundedCornerShape(32.dp)
                clip = true
                shadowElevation = ELEVATION
            },
        containerColor = MaterialTheme.colorScheme.primary,
        windowInsets = WindowInsets(0, 0, 0, 0),
        tonalElevation = 0.dp
    ) {
        loggedRoutes.forEach { screen ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    screen.icon?.let { icon ->
                        screen.titleId?.let { titleId ->
                            Icon(
                                imageVector = icon,
                                contentDescription = stringResource(titleId)
                            )
                        }
                    }
                },
                label = {
                    screen.titleId?.let { titleId ->
                        Text(
                            text = stringResource(titleId),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}