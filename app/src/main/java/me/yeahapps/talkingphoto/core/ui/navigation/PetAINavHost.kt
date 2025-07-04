package me.yeahapps.talkingphoto.core.ui.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.yeahapps.talkingphoto.features.avatars.ui.screen.TransformContainer
import me.yeahapps.talkingphoto.features.avatars.ui.screen.TransformScreen
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
            val args = it.toRoute<UploadPhotoScreen>()
            UploadPhotoContainer(
                uploadType = args.uploadType,
                modifier = Modifier
                    .commonModifier()
                    .systemBarsPadding(),
                navigateUp = { navController.navigateUp() },
                navigateToAddSound = { uri -> navController.navigate(AddSoundScreen(uri.toString())) },
                navigateToTransform = { uri -> navController.navigate(TransformScreen(uri.toString())) },
            )
        }
        composable<AddSoundScreen> {
            AddSoundContainer(
                modifier = Modifier
                    .commonModifier()
                    .systemBarsPadding(), navigateBack = {})
        }
        composable<TransformScreen> {
            TransformContainer(
                modifier = Modifier
                    .commonModifier()
                    .systemBarsPadding()
            )
        }
    }
}