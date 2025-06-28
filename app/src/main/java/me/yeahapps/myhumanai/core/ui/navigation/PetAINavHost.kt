package me.yeahapps.myhumanai.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.yeahapps.myhumanai.features.root.ui.screen.RootContainer
import me.yeahapps.myhumanai.features.root.ui.screen.RootScreen

@Composable
fun HumanAINavHost(
    startDestination: Any, navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable<RootScreen> {
            RootContainer(parentNavController = navController)
        }
    }
}