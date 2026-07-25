package fr.meteordesign.eldritchhorrorcompanion.features.diceroll.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.metroViewModel
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.DiceRollViewModel

@Composable
fun DiceRollView(
    modifier: Modifier = Modifier,
    viewModel: DiceRollViewModel = metroViewModel(),
) {

    val uiModel by viewModel.uiModelFlow.collectAsStateWithLifecycle()

    DiceRollContent(
        modifier = modifier,
        uiModel = uiModel,
        onStatusSelected = viewModel::onStatusSelected,
        onIncrementDiceCount = viewModel::onIncrementDiceCount,
        onDecrementDiceCount = viewModel::onDecrementDiceCount,
        onDieClick = viewModel::onDieClick,
        onRollDiceClick = viewModel::onRollDiceClick,
        onClearClick = viewModel::onClearClick,
    )
}
