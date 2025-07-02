package me.yeahapps.talkingphoto.core.ui.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.yeahapps.talkingphoto.features.generating.ui.screen.AddSoundContainer
import me.yeahapps.talkingphoto.features.generating.ui.screen.AddSoundScreen
import me.yeahapps.talkingphoto.features.root.ui.screen.RootContainer
import me.yeahapps.talkingphoto.features.root.ui.screen.RootScreen
import me.yeahapps.talkingphoto.features.upload.ui.screen.UploadPhotoContainer
import me.yeahapps.talkingphoto.features.upload.ui.screen.UploadPhotoScreen

@Composable
fun HumanAINavHost(
    startDestination: Any, navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable<RootScreen> {
            RootContainer(parentNavController = navController)
        }
        composable<UploadPhotoScreen> {
            UploadPhotoContainer(
                modifier = Modifier
                    .commonModifier()
                    .systemBarsPadding(),
                navigateToAddSound = { navController.navigate(AddSoundScreen(it.toString())) })
        }
        composable<AddSoundScreen> {
            AddSoundContainer(
                modifier = Modifier
                    .commonModifier()
                    .systemBarsPadding(), navigateBack = {})
        }
    }
}