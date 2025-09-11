package me.yeahapps.liveface.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.yeahapps.liveface.core.ui.navigation.HumanAINavHost

@Composable
fun HumanAIApp(navController: NavHostController, startDestination: Any, isFirstLaunch: Boolean) {
    HumanAINavHost(navController = navController, startDestination = startDestination, isFirstLaunch = isFirstLaunch)
}