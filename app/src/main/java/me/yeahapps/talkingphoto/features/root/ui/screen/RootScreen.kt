package me.yeahapps.talkingphoto.features.root.ui.screen

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
import me.yeahapps.talkingphoto.core.ui.navigation.commonModifier
import me.yeahapps.talkingphoto.features.avatars.AvatarsContainer
import me.yeahapps.talkingphoto.features.root.domain.BottomNavigationItem
import me.yeahapps.talkingphoto.features.root.ui.component.HumanAIBottomNavigation
import me.yeahapps.talkingphoto.features.settings.SettingsContainer
import me.yeahapps.talkingphoto.features.upload.domain.UploadType
import me.yeahapps.talkingphoto.features.upload.ui.screen.UploadPhotoScreen
import me.yeahapps.talkingphoto.features.upload.ui.screen.UploadedPhotosContainer
import me.yeahapps.talkingphoto.features.videos.VideosContainer

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
                UploadedPhotosContainer(
                    modifier = Modifier
                        .commonModifier()
                        .padding(bottom = innerPadding.calculateBottomPadding()),
                    navigateToUpload = { parentNavController.navigate(UploadPhotoScreen(UploadType.Upload)) })
            }
            composable(BottomNavigationItem.Avatars.route) {
                AvatarsContainer(
                    modifier = Modifier
                        .commonModifier()
                        .padding(bottom = innerPadding.calculateBottomPadding()),
                    navigateToPhotoUpload = { parentNavController.navigate(UploadPhotoScreen(UploadType.Avatar)) })
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
                        .padding(bottom = innerPadding.calculateBottomPadding()),
                    navigateToSubscriptions = {},
                    navigateToMyVideos = { navController.navigate(BottomNavigationItem.MyVideos.route) })
            }
        }
    }
}