package me.yeahapps.talkingphoto.features.onboarding.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
object OnboardingScreen

@Composable
fun OnboardingScreenContainer(modifier: Modifier = Modifier) {
    OnboardingScreenContent(modifier = modifier)
}

@Composable
private fun OnboardingScreenContent(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}