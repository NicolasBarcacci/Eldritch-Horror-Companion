package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.metroViewModel
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.TestResolverViewModel

@Composable
fun TestResolverView(
    modifier: Modifier = Modifier,
    viewModel: TestResolverViewModel = metroViewModel(),
) {

    val uiModel by viewModel.uiModelFlow.collectAsStateWithLifecycle()

    TestResolverContent(
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
