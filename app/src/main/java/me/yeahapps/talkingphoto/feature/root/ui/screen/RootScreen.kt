package me.yeahapps.talkingphoto.feature.root.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import me.yeahapps.talkingphoto.core.ui.navigation.commonModifier
import me.yeahapps.talkingphoto.feature.avatars.ui.screen.AvatarsContainer
import me.yeahapps.talkingphoto.feature.root.domain.BottomNavigationItem
import me.yeahapps.talkingphoto.feature.root.ui.component.HumanAIBottomNavigation
import me.yeahapps.talkingphoto.feature.settings.SettingsContainer
import me.yeahapps.talkingphoto.feature.subscription.ui.screen.SubscriptionsContainer
import me.yeahapps.talkingphoto.feature.upload.domain.UploadType
import me.yeahapps.talkingphoto.feature.upload.ui.screen.UploadPhotoScreen
import me.yeahapps.talkingphoto.feature.upload.ui.screen.UploadedPhotosContainer
import me.yeahapps.talkingphoto.feature.videos.ui.screen.VideoInfoScreen
import me.yeahapps.talkingphoto.feature.videos.ui.screen.VideosContainer

@Serializable
object RootScreen

@Composable
fun RootContainer(
    modifier: Modifier = Modifier,
    parentNavController: NavHostController,
    isFirstLaunch: Boolean = false
) {
    RootContent(modifier = modifier, parentNavController = parentNavController, isFirstLaunch = isFirstLaunch)
}

@Composable
private fun RootContent(
    modifier: Modifier = Modifier,
    parentNavController: NavHostController,
    isFirstLaunch: Boolean = false
) {
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

    var isFirstLaunch by rememberSaveable { mutableStateOf(isFirstLaunch) }
    var isSubscriptionsScreenVisible by remember { mutableStateOf(isFirstLaunch) }

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
                        .padding(bottom = innerPadding.calculateBottomPadding()),
                    navigateToVideoInfo = { parentNavController.navigate(VideoInfoScreen(it)) }
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

    if (isSubscriptionsScreenVisible) {
        SubscriptionsContainer(
            modifier = Modifier
                .commonModifier()
                .fillMaxSize(),
            onScreenClose = { isSubscriptionsScreenVisible = false })
    }
}