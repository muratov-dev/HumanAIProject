package me.yeahapps.myhumanai.features.upload.ui.component.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import me.yeahapps.myhumanai.R
import me.yeahapps.myhumanai.core.ui.theme.HumanAITheme

@Composable
fun UploadedPhotoCard(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .aspectRatio(1f / 1.22f)
            .clip(RoundedCornerShape(32.dp))
            .background(color = Color(0xFF131238))
            .clickable(onClick = onClick), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(color = Color(0xFF7C8099), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                tint = HumanAITheme.colors.backgroundPrimary,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .matchParentSize()
            )
        }
    }
}