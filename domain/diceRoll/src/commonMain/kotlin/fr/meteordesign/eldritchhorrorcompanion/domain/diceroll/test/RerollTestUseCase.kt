package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test

import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.RollD6UseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.TestResult

@Inject
class RerollTestUseCase(
    private val rollD6UseCase: RollD6UseCase,
    private val calculateTestSuccessCountUseCase: CalculateTestSuccessCountUseCase,
) {

    operator fun invoke(
        previousRolls: List<Int>,
        indicesToReroll: Set<Int>,
        status: Status,
    ): TestResult {
        val rolls = previousRolls.mapIndexed { index, roll ->
            if (index in indicesToReroll) {
                rollD6UseCase()
            } else {
                roll
            }
        }

        return TestResult(
            rolls = rolls,
            successCount = calculateTestSuccessCountUseCase(rolls = rolls, status = status),
        )
    }
}
