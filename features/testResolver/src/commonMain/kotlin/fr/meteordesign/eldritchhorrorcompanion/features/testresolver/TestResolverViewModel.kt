package fr.meteordesign.eldritchhorrorcompanion.features.testresolver

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.RerollTestUseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.ResolveTestUseCase
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper.IndicesToRerollMapper
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper.StatusMapper
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper.TestResultMapper
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.Configuration.Status
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class TestResolverViewModel(
    private val resolveTestUseCase: ResolveTestUseCase,
    private val rerollTestUseCase: RerollTestUseCase,
    private val statusMapper: StatusMapper,
    private val testResultMapper: TestResultMapper,
    private val indicesToRerollMapper: IndicesToRerollMapper,
) : ViewModel() {

    val uiModelFlow: StateFlow<TestResolverUiModel>
        field = MutableStateFlow(TestResolverUiModel())

    fun onStatusSelected(status: Status) {
        uiModelFlow.update { uiModel ->
            uiModel.copy(
                configuration = uiModel.configuration.copy(selectedStatus = status),
                testResult = null,
            )
        }
    }

    fun onIncrementDiceCount() {
        uiModelFlow.update { uiModel ->
            uiModel.copy(
                configuration = uiModel.configuration.copy(
                    diceCount = (uiModel.configuration.diceCount + 1)
                        .coerceAtMost(MaxDiceCount),
                ),
                testResult = null,
            )
        }
    }

    fun onDecrementDiceCount() {
        uiModelFlow.update { uiModel ->
            uiModel.copy(
                configuration = uiModel.configuration.copy(
                    diceCount = (uiModel.configuration.diceCount - 1)
                        .coerceAtLeast(MinDiceCount),
                ),
                testResult = null,
            )
        }
    }

    fun onDieClick(index: Int) {
        uiModelFlow.update { uiModel ->
            val testResult = uiModel.testResult ?: return@update uiModel
            val dice = testResult.dice.mapIndexed { i, die ->
                when (i) {
                    index -> die.copy(selected = !die.selected)
                    else -> die
                }
            }

            uiModel.copy(testResult = testResult.copy(dice = dice))
        }
    }

    fun onClearClick() {
        uiModelFlow.update { uiModel ->
            uiModel.copy(testResult = null)
        }
    }

    fun onRollDiceClick() {
        val uiModel = uiModelFlow.value
        val previousTestResult = uiModel.testResult

        when (previousTestResult) {
            null -> roll(uiModel)
            else -> reroll(uiModel, previousTestResult)
        }
    }

    private fun roll(uiModel: TestResolverUiModel) {
        when (
            val result =
                resolveTestUseCase(
                    status = statusMapper.map(uiModel.configuration.selectedStatus),
                    diceCount = uiModel.configuration.diceCount,
                )
        ) {
            is Result.Success -> {
                uiModelFlow.update {
                    it.copy(testResult = testResultMapper.map(result.value))
                }
            }

            is Result.Failure -> Unit
        }
    }

    private fun reroll(uiModel: TestResolverUiModel, previousTestResult: TestResult) {
        val result = rerollTestUseCase(
            status = statusMapper.map(uiModel.configuration.selectedStatus),
            previousRolls = previousTestResult.dice.map { it.roll },
            indicesToReroll = indicesToRerollMapper.map(previousTestResult),
        )

        uiModelFlow.update {
            it.copy(testResult = testResultMapper.map(result))
        }
    }

    private companion object {
        const val MinDiceCount = 1
        const val MaxDiceCount = 20
    }
}
