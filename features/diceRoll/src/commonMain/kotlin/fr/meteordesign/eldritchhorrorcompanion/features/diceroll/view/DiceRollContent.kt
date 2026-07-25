package fr.meteordesign.eldritchhorrorcompanion.features.diceroll.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.scaffold.EhcScaffold
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.theme.EhcTheme
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils.ehcFillMaxSize
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.DiceRollUiModel

@Composable
fun DiceRollContent(
    modifier: Modifier = Modifier,
    uiModel: DiceRollUiModel,
    onRollDiceClick: () -> Unit,
) {
    EhcScaffold(
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .ehcFillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                uiModel.result?.let {
                    Text("$it")
                }
            }
            Button(
                modifier = Modifier
                    .padding(24.dp),
                onClick = onRollDiceClick,
            ) {
                Text("Roll")
            }
        }
    }
}

@Preview
@Composable
private fun DiceRollContentPreview() {
    EhcTheme {
        DiceRollContent(
            uiModel = DiceRollUiModel(
                result = 5,
            ),
            onRollDiceClick = {},
        )
    }
}
