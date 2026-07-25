package fr.meteordesign.eldritchhorrorcompanion.features.diceroll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.scaffold.EhcScaffold
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.theme.EhcTheme

@Composable
fun DiceRollContent(
    modifier: Modifier = Modifier,
) {
    EhcScaffold(
        modifier = modifier,
    ) { paddingValues ->
        Box(
            modifier.padding(paddingValues),
        ) {
            Text("Dice Roll")
        }
    }
}

@Preview
@Composable
private fun DiceRollContentPreview() {
    EhcTheme {
        DiceRollContent()
    }
}
