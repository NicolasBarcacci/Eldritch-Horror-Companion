package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import eldritchhorrorcompanion.designsystem.core.generated.resources.ic_lock_closed
import eldritchhorrorcompanion.designsystem.core.generated.resources.ic_lock_opened
import eldritchhorrorcompanion.features.core.generated.resources.Res
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_clear_cta
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_reroll_cta
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_successes_count
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.button.EhcButtonPrimary
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.button.EhcButtonSecondary
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.scaffold.EhcScaffold
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.theme.EhcTheme
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils.ehcFillMaxSize
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.TestResolverUiModel
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.TestResolverUiModel.Configuration.Status
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.TestResolverUiModel.TestResult
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import eldritchhorrorcompanion.designsystem.core.generated.resources.Res as DesignSystemRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestResolverContent(
    modifier: Modifier = Modifier,
    uiModel: TestResolverUiModel,
    onStatusSelected: (Status) -> Unit,
    onIncrementDiceCount: () -> Unit,
    onDecrementDiceCount: () -> Unit,
    onDieClick: (Int) -> Unit,
    onRollDiceClick: () -> Unit,
    onClearClick: () -> Unit,
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
                uiModel.configuration.statuses.forEachIndexed { index, status ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = uiModel.configuration.statuses.size,
                        ),
                        selected = uiModel.configuration.selectedStatus == status,
                        onClick = { onStatusSelected(status) },
                    ) {
                        Text(stringResource(status.label))
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
                    enabled = uiModel.configuration.diceCount > 1,
                ) {
                    Text("-")
                }
                Text(text = "${uiModel.configuration.diceCount}")
                OutlinedButton(
                    onClick = onIncrementDiceCount,
                ) {
                    Text("+")
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                val result = uiModel.testResult

                FlowRow(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (result == null) {
                        repeat(uiModel.configuration.diceCount) {
                            DieFace(text = "?")
                        }
                    } else {
                        result.dice.forEachIndexed { index, die ->
                            DieFace(
                                text = "${die.roll}",
                                selected = die.selected,
                                onClick = { onDieClick(index) },
                            )
                        }
                    }
                }

                result?.let {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(y = 40.dp),
                        text = stringResource(
                            Res.string.test_resolver_successes_count,
                            it.successCount,
                        ),
                    )
                }

                EhcButtonSecondary(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(y = 72.dp)
                        .padding(end = 16.dp),
                    onClick = onClearClick,
                    enabled = uiModel.clearEnabled,
                ) {
                    Text(stringResource(Res.string.test_resolver_clear_cta))
                }
            }

            EhcButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                onClick = onRollDiceClick,
                enabled = uiModel.rollEnabled,
            ) {
                Text(stringResource(uiModel.rollLabel))
            }
        }
    }
}

@Composable
private fun DieFace(
    text: String,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .let { boxModifier ->
                if (onClick != null) boxModifier.clickable(onClick = onClick) else boxModifier
            }
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text)

        if (onClick != null) {
            Icon(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(2.dp)
                    .size(12.dp),
                painter = painterResource(
                    if (selected) DesignSystemRes.drawable.ic_lock_opened else DesignSystemRes.drawable.ic_lock_closed,
                ),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun TestResolverContentBeforeRollPreview() {
    EhcTheme {
        TestResolverContent(
            uiModel = TestResolverUiModel(
                configuration = TestResolverUiModel.Configuration(
                    diceCount = 3,
                    selectedStatus = Status.None,
                    statuses = listOf(Status.Cursed, Status.None, Status.Blessed),
                ),
            ),
            onStatusSelected = {},
            onIncrementDiceCount = {},
            onDecrementDiceCount = {},
            onDieClick = {},
            onRollDiceClick = {},
            onClearClick = {},
        )
    }
}

@Preview
@Composable
private fun TestResolverContentAfterRollPreview() {
    EhcTheme {
        TestResolverContent(
            uiModel = TestResolverUiModel(
                configuration = TestResolverUiModel.Configuration(
                    diceCount = 3,
                    selectedStatus = Status.None,
                    statuses = listOf(Status.Cursed, Status.None, Status.Blessed),
                ),
                testResult = TestResult(
                    dice = listOf(
                        TestResult.Die(roll = 2, selected = true),
                        TestResult.Die(roll = 5, selected = false),
                        TestResult.Die(roll = 6, selected = false),
                    ),
                    successCount = 2,
                ),
                rollLabel = Res.string.test_resolver_reroll_cta,
            ),
            onStatusSelected = {},
            onIncrementDiceCount = {},
            onDecrementDiceCount = {},
            onDieClick = {},
            onRollDiceClick = {},
            onClearClick = {},
        )
    }
}
