package fr.meteordesign.eldritchhorrorcompanion.features.diceroll

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import eldritchhorrorcompanion.features.core.generated.resources.Res
import eldritchhorrorcompanion.features.core.generated.resources.dice_roll_reroll_cta
import eldritchhorrorcompanion.features.core.generated.resources.dice_roll_roll_cta
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.RerollTestUseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.ResolveTestUseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.Status as DomainStatus
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.DiceRollUiModel.Configuration.Status
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.DiceRollUiModel.TestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

private val Status.domainStatus: DomainStatus
    get() = when (this) {
        Status.Cursed -> DomainStatus.CURSED
        Status.None -> DomainStatus.NONE
        Status.Blessed -> DomainStatus.BLESSED
    }

private fun List<Int>.toDice(): List<TestResult.Die> =
    map { roll -> TestResult.Die(roll = roll, selected = false) }

private val TestResult.indicesToReroll: Set<Int>
    get() = dice.withIndex().filter { (_, die) -> die.selected }.map { (index, _) -> index }.toSet()

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class DiceRollViewModel(
    private val resolveTestUseCase: ResolveTestUseCase,
    private val rerollTestUseCase: RerollTestUseCase,
) : ViewModel() {

    private val _uiModelFlow = MutableStateFlow(DiceRollUiModel())
    val uiModelFlow: StateFlow<DiceRollUiModel>
        get() = _uiModelFlow

    fun onStatusSelected(status: Status) {
        _uiModelFlow.update { uiModel ->
            uiModel.copy(
                configuration = uiModel.configuration.copy(selectedStatus = status),
                testResult = null,
                rollEnabled = true,
                rollLabel = Res.string.dice_roll_roll_cta,
            )
        }
    }

    fun onIncrementDiceCount() {
        _uiModelFlow.update { uiModel ->
            uiModel.copy(
                configuration = uiModel.configuration.copy(diceCount = uiModel.configuration.diceCount + 1),
                testResult = null,
                rollEnabled = true,
                rollLabel = Res.string.dice_roll_roll_cta,
            )
        }
    }

    fun onDecrementDiceCount() {
        _uiModelFlow.update { uiModel ->
            uiModel.copy(
                configuration = uiModel.configuration.copy(
                    diceCount = (uiModel.configuration.diceCount - 1).coerceAtLeast(MinDiceCount),
                ),
                testResult = null,
                rollEnabled = true,
                rollLabel = Res.string.dice_roll_roll_cta,
            )
        }
    }

    fun onDieClick(index: Int) {
        _uiModelFlow.update { uiModel ->
            val testResult = uiModel.testResult ?: return@update uiModel
            val dice = testResult.dice.mapIndexed { i, die ->
                if (i == index) die.copy(selected = !die.selected) else die
            }

            uiModel.copy(
                testResult = testResult.copy(dice = dice),
                rollEnabled = dice.any { it.selected },
            )
        }
    }

    fun onClearClick() {
        _uiModelFlow.update { uiModel ->
            uiModel.copy(
                testResult = null,
                rollEnabled = true,
                rollLabel = Res.string.dice_roll_roll_cta,
            )
        }
    }

    fun onRollDiceClick() {
        val uiModel = _uiModelFlow.value
        val previousTestResult = uiModel.testResult

        if (previousTestResult == null) {
            when (
                val result =
                    resolveTestUseCase(
                        diceCount = uiModel.configuration.diceCount,
                        status = uiModel.configuration.selectedStatus.domainStatus,
                    )
            ) {
                is Result.Success -> {
                    _uiModelFlow.update {
                        it.copy(
                            testResult = TestResult(
                                dice = result.value.rolls.toDice(),
                                successCount = result.value.successCount,
                            ),
                            rollEnabled = false,
                            rollLabel = Res.string.dice_roll_reroll_cta,
                        )
                    }
                }

                is Result.Failure -> Unit
            }
        } else {
            val result = rerollTestUseCase(
                previousRolls = previousTestResult.dice.map { it.roll },
                indicesToReroll = previousTestResult.indicesToReroll,
                status = uiModel.configuration.selectedStatus.domainStatus,
            )

            _uiModelFlow.update {
                it.copy(
                    testResult = TestResult(
                        dice = result.rolls.toDice(),
                        successCount = result.successCount,
                    ),
                    rollEnabled = false,
                )
            }
        }
    }

    private companion object {
        const val MinDiceCount = 1
    }
}
