package fr.meteordesign.eldritchhorrorcompanion.features.diceroll

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import fr.meteordesign.eldritchhorrorcompanion.domain.core.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.core._di.AppScope
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest.ResolveTestUseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class DiceRollViewModel(
    private val resolveTestUseCase: ResolveTestUseCase,
) : ViewModel() {

    private val _uiModelFlow = MutableStateFlow(DiceRollUiModel())
    val uiModelFlow: StateFlow<DiceRollUiModel>
        get() = _uiModelFlow

    fun onRollDiceClick() {
        when (val result = resolveTestUseCase(diceCount = 10, status = Status.NONE)) {
            is Result.Success -> {
                _uiModelFlow.update { uiModel ->
                    uiModel.copy(
                        result = result.value.successCount,
                    )
                }
            }

            is Result.Failure -> Unit
        }
    }
}
