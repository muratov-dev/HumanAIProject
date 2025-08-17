package me.yeahapps.talkingphoto.core.ui.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.yeahapps.talkingphoto.feature.avatars.ui.screen.TransformContainer
import me.yeahapps.talkingphoto.feature.avatars.ui.screen.TransformScreen
import me.yeahapps.talkingphoto.feature.generating.ui.screen.AddSoundContainer
import me.yeahapps.talkingphoto.feature.generating.ui.screen.AddSoundScreen
import me.yeahapps.talkingphoto.feature.generating.ui.screen.CreatingVideoContainer
import me.yeahapps.talkingphoto.feature.generating.ui.screen.CreatingVideoScreen
import me.yeahapps.talkingphoto.feature.onboarding.ui.screen.OnboardingContainer
import me.yeahapps.talkingphoto.feature.onboarding.ui.screen.OnboardingScreen
import me.yeahapps.talkingphoto.feature.root.ui.screen.RootContainer
import me.yeahapps.talkingphoto.feature.root.ui.screen.RootScreen
import me.yeahapps.talkingphoto.feature.upload.ui.screen.UploadPhotoContainer
import me.yeahapps.talkingphoto.feature.upload.ui.screen.UploadPhotoScreen
import me.yeahapps.talkingphoto.feature.videos.ui.screen.VideoInfoContainer
import me.yeahapps.talkingphoto.feature.videos.ui.screen.VideoInfoScreen

@Composable
fun HumanAINavHost(
    startDestination: Any, navController: NavHostController, isFirstLaunch: Boolean, modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable<OnboardingScreen> {
            OnboardingContainer(
                modifier = Modifier.commonModifier(), navigateToSubscriptions = { navController.navigate(RootScreen) })
        }
        composable<RootScreen> {
            RootContainer(parentNavController = navController, isFirstLaunch = isFirstLaunch)
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
                    .systemBarsPadding(),
                navigateUp = { navController.navigateUp() },
                startGenerating = { audioScript, audioUri, imageUri ->
                    navController.navigate(CreatingVideoScreen(audioScript, audioUri, imageUri))
                })
        }
        composable<TransformScreen> {
            TransformContainer(
                modifier = Modifier
                    .commonModifier()
                    .systemBarsPadding(),
                navigateUp = { navController.navigateUp() },
                navigateToGenerating = { navController.navigate(AddSoundScreen(it)) })
        }

        composable<CreatingVideoScreen> {
            CreatingVideoContainer(
                modifier = Modifier
                    .commonModifier()
                    .systemBarsPadding(),
                navigateUp = { navController.navigateUp() },
                navigateToVideo = {
                    navController.navigate(VideoInfoScreen(it)) {
                        navController.popBackStack(RootScreen, false)
                    }
                })
        }

        composable<VideoInfoScreen> {
            VideoInfoContainer(
                Modifier
                    .commonModifier()
                    .systemBarsPadding(), navigateUp = { navController.navigateUp() })
        }
    }
}