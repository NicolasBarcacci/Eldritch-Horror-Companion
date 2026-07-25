package fr.meteordesign.eldritchhorrorcompanion.features.diceroll

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import fr.meteordesign.eldritchhorrorcompanion.domain.core.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.core._di.AppScope
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest.ResolveTestUseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest.Status
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.DiceRollUiModel.TestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

private const val MIN_DICE_COUNT = 1

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class DiceRollViewModel(
    private val resolveTestUseCase: ResolveTestUseCase,
) : ViewModel() {

    private val _uiModelFlow = MutableStateFlow(DiceRollUiModel())
    val uiModelFlow: StateFlow<DiceRollUiModel>
        get() = _uiModelFlow

    fun onStatusSelected(status: Status) {
        _uiModelFlow.update { uiModel ->
            uiModel.copy(
                status = status,
                testResult = null,
            )
        }
    }

    fun onIncrementDiceCount() {
        _uiModelFlow.update { uiModel ->
            uiModel.copy(
                diceCount = uiModel.diceCount + 1,
                testResult = null,
            )
        }
    }

    fun onDecrementDiceCount() {
        _uiModelFlow.update { uiModel ->
            uiModel.copy(
                diceCount = (uiModel.diceCount - 1).coerceAtLeast(MIN_DICE_COUNT),
                testResult = null,
            )
        }
    }

    fun onRollDiceClick() {
        val uiModel = _uiModelFlow.value

        when (
            val result = resolveTestUseCase(diceCount = uiModel.diceCount, status = uiModel.status)
        ) {
            is Result.Success -> {
                _uiModelFlow.update {
                    it.copy(
                        testResult = TestResult(
                            rolls = result.value.rolls,
                            successCount = result.value.successCount,
                        ),
                    )
                }
            }

            is Result.Failure -> Unit
        }
    }
}
