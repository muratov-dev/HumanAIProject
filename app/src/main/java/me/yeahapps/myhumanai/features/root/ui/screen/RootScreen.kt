package me.yeahapps.myhumanai.features.root.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import me.yeahapps.myhumanai.features.avatars.AvatarsContainer
import me.yeahapps.myhumanai.features.root.domain.BottomNavigationItem
import me.yeahapps.myhumanai.features.root.ui.component.HumanAIBottomNavigation
import me.yeahapps.myhumanai.features.settings.SettingsContainer
import me.yeahapps.myhumanai.features.upload.ui.screen.UploadContainer
import me.yeahapps.myhumanai.features.videos.VideosContainer
import me.yeahapps.myhumanai.core.ui.navigation.commonModifier

@Serializable
object RootScreen

@Composable
fun RootContainer(modifier: Modifier = Modifier, parentNavController: NavHostController) {
    RootContent(modifier = modifier, parentNavController = parentNavController)
}

@Composable
private fun RootContent(modifier: Modifier = Modifier, parentNavController: NavHostController) {
    val items = listOf(
        BottomNavigationItem.Upload,
        BottomNavigationItem.Avatars,
        BottomNavigationItem.MyVideos,
        BottomNavigationItem.Settings,
    )
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinationIndex =
        navBackStackEntry?.destination?.route?.let { route -> items.indexOfFirst { it.route == route } } ?: 0

    Scaffold(modifier = modifier, bottomBar = {
        HumanAIBottomNavigation(items = items, selectedItem = currentDestinationIndex, onItemClick = {
            navController.navigate(items[it].route) {
                launchSingleTop = true
                restoreState = true
            }
        })
    }) { innerPadding ->
        NavHost(
            navController, startDestination = BottomNavigationItem.Upload.route, modifier = Modifier.fillMaxSize()
        ) {
            composable(BottomNavigationItem.Upload.route) {
                UploadContainer(
                    modifier = Modifier
                        .commonModifier()
                        .padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
            composable(BottomNavigationItem.Avatars.route) {
                AvatarsContainer(
                    modifier = Modifier
                        .commonModifier()
                        .padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
            composable(BottomNavigationItem.MyVideos.route) {
                VideosContainer(
                    modifier = Modifier
                        .commonModifier()
                        .padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
            composable(BottomNavigationItem.Settings.route) {
                SettingsContainer(
                    modifier = Modifier
                        .commonModifier()
                        .padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }
    }
}