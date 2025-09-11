package me.yeahapps.liveface.app

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.yeahapps.liveface.core.ui.theme.HumanAITheme
import me.yeahapps.liveface.feature.onboarding.ui.screen.OnboardingScreen
import me.yeahapps.liveface.feature.root.ui.screen.RootScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
        setContent {
            val navController = rememberNavController()
            HumanAITheme {
                HumanAIApp(
                    navController,
                    startDestination = if (isFirstLaunch) OnboardingScreen else RootScreen,
                    isFirstLaunch = isFirstLaunch,
                )
            }
        }
    }
}