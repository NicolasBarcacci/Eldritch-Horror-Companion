package fr.meteordesign.eldritchhorrorcompanion.features.diceroll

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import fr.meteordesign.eldritchhorrorcompanion.domain.core._di.AppScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class DiceRollViewModel : ViewModel() {

    private val _uiModelFlow = MutableStateFlow(DiceRollUiModel())
    val uiModelFlow: StateFlow<DiceRollUiModel>
        get() = _uiModelFlow

    fun onRollDiceClick() {
        _uiModelFlow.update { uiModel ->
            uiModel.copy(
                result = Random.nextInt(6),
            )
        }
    }
}
