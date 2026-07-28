package fr.meteordesign.eldritchhorrorcompanion.designsystem.core.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EhcText(
    modifier: Modifier = Modifier,
    text: EhcTextValue,
    textStyle: EhcTextStyle,
) {
    Text(
        modifier = modifier,
        text = text.getString(),
        style = textStyle.textStyle,
    )
}
