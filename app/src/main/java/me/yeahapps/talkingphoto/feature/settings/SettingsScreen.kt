package me.yeahapps.talkingphoto.feature.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.talkingphoto.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.talkingphoto.feature.settings.component.SettingsButton
import me.yeahapps.talkingphoto.feature.upload.ui.action.SettingsAction
import me.yeahapps.talkingphoto.feature.upload.ui.event.SettingsEvent
import me.yeahapps.talkingphoto.feature.upload.ui.state.SettingsState
import me.yeahapps.talkingphoto.feature.upload.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsContainer(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateToSubscriptions: () -> Unit = {},
    navigateToMyVideos: () -> Unit = {},
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            SettingsAction.NavigateToMyVideos -> navigateToMyVideos()
            SettingsAction.NavigateToSubscriptions -> navigateToSubscriptions()
            null -> {}
        }
    }
    SettingsContent(
        modifier = modifier, state = state, onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier, state: SettingsState = SettingsState(), onEvent: (SettingsEvent) -> Unit = {}
) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = { HumanAIPrimaryTopBar(title = stringResource(R.string.settings_headline)) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            if (!state.hasSubscription) {
                Image(
                    painter = painterResource(R.drawable.im_card_pro),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .aspectRatio(2f / 1f)
                        .clip(RoundedCornerShape(32.dp))
                        .clickable { onEvent(SettingsEvent.NavigateToSubscriptions) })
                Spacer(modifier = Modifier.height(24.dp))
            }
            SettingsButton(
                text = "My Works (${state.videosCount})",
                onClick = { onEvent(SettingsEvent.NavigateToMyVideos) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                startContent = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_my_works), contentDescription = null)
                },
                endContent = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right), contentDescription = null)
                })
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SettingsButton(text = "Share", onClick = {
                    val packageName = context.packageName
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "I recommend the application")
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Try this application: https://play.google.com/store/apps/details?id=$packageName"
                        )
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share with"))
                }, modifier = Modifier.fillMaxWidth(), startContent = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_share), contentDescription = null)
                })
                SettingsButton(text = "Rate Us", onClick = {
                    val packageName = context.packageName
                    val uri = "market://details?id=$packageName".toUri()
                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        val webIntent = Intent(
                            Intent.ACTION_VIEW, "https://play.google.com/store/apps/details?id=$packageName".toUri()
                        )
                        context.startActivity(webIntent)
                    }
                }, modifier = Modifier.fillMaxWidth(), startContent = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_rate), contentDescription = null)
                })
                SettingsButton(text = "Terms of Use", onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, "https://www.yeahapps.me/terms".toUri())
                    context.startActivity(intent)
                }, modifier = Modifier.fillMaxWidth(), startContent = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_terms), contentDescription = null)
                })
                SettingsButton(text = "Privacy Policy", onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, "http://yeahapps.me/privacy".toUri())
                    context.startActivity(intent)
                }, modifier = Modifier.fillMaxWidth(), startContent = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_policy), contentDescription = null)
                })
            }
        }
    }
}