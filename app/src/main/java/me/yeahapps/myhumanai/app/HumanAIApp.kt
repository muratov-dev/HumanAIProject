package me.yeahapps.myhumanai.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.yeahapps.myhumanai.core.ui.navigation.HumanAINavHost

@Composable
fun HumanAIApp(navController: NavHostController, startDestination: Any) {

    HumanAINavHost(navController = navController, startDestination = startDestination)
}