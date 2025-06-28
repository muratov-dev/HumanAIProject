package me.yeahapps.myhumanai.app

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.yeahapps.myhumanai.core.ui.component.RequestInAppReview
import me.yeahapps.myhumanai.core.ui.theme.HumanAITheme
import me.yeahapps.myhumanai.features.onboarding.ui.OnboardingScreen
import me.yeahapps.myhumanai.features.root.ui.RootScreen
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
                )
                var showDialog by remember { mutableStateOf(true) }
                if (!isFirstLaunch) {
                    RequestInAppReview(showDialog, onDismiss = { showDialog = false }, context = LocalContext.current)
                }
            }
        }
    }
}