package me.yeahapps.liveface.feature.subscription.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.yeahapps.liveface.core.ui.theme.HumanAITheme

@Composable
fun SubscriptionItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    weeklyPrice: String,
    discountPercent: Int?,
    selected: Boolean,
    onClick: () -> Unit
) {
    val selectedBackgroundColor = HumanAITheme.colors.buttonPrimaryDefault.copy(alpha = 0.1f)
    val unselectedBackgroundColor = Color(0x00FFFFFF)
    val backgroundColor = if (selected) selectedBackgroundColor else unselectedBackgroundColor

    val selectedBorderColor = HumanAITheme.colors.buttonPrimaryDefault
    val unselectedBorderColor = Color.White.copy(alpha = 0.5f)
    val borderColor = if (selected) selectedBorderColor else unselectedBorderColor

    val shape = RoundedCornerShape(100.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = shape)
            .clip(shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = Color.White, style = HumanAITheme.typography.bodyRegular)
            Text(text = subtitle, color = Color.White.copy(alpha = 0.5f), style = HumanAITheme.typography.bodySmall)
        }
        discountPercent?.let {
            Text(
                text = "${it}% OFF",
                color = Color.White,
                style = HumanAITheme.typography.bodySmallBold,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .background(color = Color(0xFFF34949), RoundedCornerShape(100.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = weeklyPrice,
                textAlign = TextAlign.End,
                color = Color.White,
                style = HumanAITheme.typography.bodyRegular
            )
            Text(
                text = "per week",
                textAlign = TextAlign.End,
                color = Color.White.copy(alpha = 0.5f),
                style = HumanAITheme.typography.labelMedium
            )
        }
    }
}