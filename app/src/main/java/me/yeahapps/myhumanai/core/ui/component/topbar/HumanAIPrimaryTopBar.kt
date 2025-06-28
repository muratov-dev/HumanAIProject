package me.yeahapps.myhumanai.core.ui.component.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.yeahapps.myhumanai.core.ui.theme.HumanAITheme

@Composable
fun HumanAIPrimaryTopBar(modifier: Modifier = Modifier, title: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = title, style = HumanAITheme.typography.headlineExtraBold, color = HumanAITheme.colors.textPrimary)
    }
}