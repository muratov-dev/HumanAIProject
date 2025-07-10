package me.yeahapps.talkingphoto.feature.crop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.attafitamim.krop.core.crop.CropState
import com.attafitamim.krop.core.crop.CropperStyle
import com.attafitamim.krop.core.crop.DefaultCropperStyle
import com.attafitamim.krop.core.crop.rotLeft
import com.attafitamim.krop.ui.CropperPreview
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.talkingphoto.core.ui.component.button.icons.HumanAIIconButton
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAISecondaryTopBar

@Composable
fun PhotoCropperScreen(
    state: CropState,
    modifier: Modifier = Modifier,
    style: CropperStyle = DefaultCropperStyle,
) {
    LaunchedEffect(Unit) {
        state.setInitialState(style)
    }

    Scaffold(modifier = modifier, topBar = {
        HumanAISecondaryTopBar(
            title = stringResource(R.string.crop_title),
            navigationIcon = { HumanAIIconButton(icon = R.drawable.ic_arrow_back, onClick = { state.done(false) }) },
            actions = { HumanAIIconButton(icon = R.drawable.ic_rotate, onClick = { state.rotLeft() }) })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp)
        ) {
            Spacer(Modifier.size(32.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .clipToBounds()
            ) {
                CropperPreview(state = state, modifier = Modifier.fillMaxSize())
            }
            Spacer(Modifier.size(12.dp))
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cropper_decoration),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(24.dp))
            HumanAIPrimaryButton(
                centerContent = stringResource(R.string.common_done),
                onClick = { state.done(true) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}