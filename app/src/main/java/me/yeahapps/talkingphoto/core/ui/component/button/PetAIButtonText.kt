package me.yeahapps.talkingphoto.core.ui.component.button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme

@Composable
fun HumanAIButtonText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = HumanAITheme.typography.bodySemiBold
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        textAlign = textAlign,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun HumanAIButtonText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = HumanAITheme.typography.bodySemiBold
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        textAlign = textAlign,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}