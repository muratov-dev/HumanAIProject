package me.yeahapps.myhumanai.features.root.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.yeahapps.myhumanai.features.root.domain.BottomNavigationItem

@Composable
fun HumanAIBottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
    colors: HumanAINavBarColors = HumanAINavBarDefaults.colors(),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(color = colors.containerColor)
            .padding(top = 8.dp, bottom = 4.dp)
    ) {
        items.forEachIndexed { index, item ->
            HumanAINavBarItem(
                icon = item.icon,
                label = item.label,
                onClick = { onItemClick(index) },
                modifier = Modifier.weight(1f),
                selected = selectedItem == index,
            )
        }
    }
}