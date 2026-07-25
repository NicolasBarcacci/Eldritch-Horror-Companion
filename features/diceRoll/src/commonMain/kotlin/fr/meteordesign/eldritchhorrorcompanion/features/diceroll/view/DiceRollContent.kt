package fr.meteordesign.eldritchhorrorcompanion.features.diceroll.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.scaffold.EhcScaffold
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.theme.EhcTheme
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils.ehcFillMaxSize
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest.Status
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.DiceRollUiModel
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.DiceRollUiModel.TestResult

private val statusOptions = listOf(Status.CURSED, Status.NONE, Status.BLESSED)

private val Status.label: String
    get() = when (this) {
        Status.CURSED -> "Maudit"
        Status.NONE -> "Rien"
        Status.BLESSED -> "Béni"
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceRollContent(
    modifier: Modifier = Modifier,
    uiModel: DiceRollUiModel,
    onStatusSelected: (Status) -> Unit,
    onIncrementDiceCount: () -> Unit,
    onDecrementDiceCount: () -> Unit,
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
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.padding(top = 16.dp),
            ) {
                statusOptions.forEachIndexed { index, status ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = statusOptions.size,
                        ),
                        selected = uiModel.status == status,
                        onClick = { onStatusSelected(status) },
                    ) {
                        Text(status.label)
                    }
                }
            }

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                OutlinedButton(
                    onClick = onDecrementDiceCount,
                    enabled = uiModel.diceCount > 1,
                ) {
                    Text("-")
                }
                Text(text = "${uiModel.diceCount}")
                OutlinedButton(onClick = onIncrementDiceCount) {
                    Text("+")
                }
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val result = uiModel.testResult

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        if (result == null) {
                            repeat(uiModel.diceCount) {
                                DieFace(text = "?")
                            }
                        } else {
                            result.rolls.forEach { roll ->
                                DieFace(text = "$roll")
                            }
                        }
                    }

                    result?.let {
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = "Succès : ${it.successCount}",
                        )
                    }
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

@Composable
private fun DieFace(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun DiceRollContentBeforeRollPreview() {
    EhcTheme {
        DiceRollContent(
            uiModel = DiceRollUiModel(diceCount = 3),
            onStatusSelected = {},
            onIncrementDiceCount = {},
            onDecrementDiceCount = {},
            onRollDiceClick = {},
        )
    }
}

@Preview
@Composable
private fun DiceRollContentAfterRollPreview() {
    EhcTheme {
        DiceRollContent(
            uiModel = DiceRollUiModel(
                diceCount = 3,
                testResult = TestResult(
                    rolls = listOf(2, 5, 6),
                    successCount = 2,
                ),
            ),
            onStatusSelected = {},
            onIncrementDiceCount = {},
            onDecrementDiceCount = {},
            onRollDiceClick = {},
        )
    }
}
