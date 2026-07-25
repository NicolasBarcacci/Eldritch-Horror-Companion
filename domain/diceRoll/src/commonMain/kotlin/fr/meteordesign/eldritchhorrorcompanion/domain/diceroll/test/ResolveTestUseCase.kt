package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test

import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.core.RollD6UseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.ResolveTestError
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.TestResult

@Inject
class ResolveTestUseCase(
    private val rollD6UseCase: RollD6UseCase,
    private val calculateTestSuccessCountUseCase: CalculateTestSuccessCountUseCase,
) {

    operator fun invoke(
        diceCount: Int,
        status: Status,
    ): Result<TestResult, ResolveTestError> {
        if (diceCount < 1) {
            return Result.Failure(ResolveTestError.InvalidDiceCount)
        }

        val rolls = List(diceCount) { rollD6UseCase() }

        return Result.Success(
            TestResult(
                rolls = rolls,
                successCount = calculateTestSuccessCountUseCase(rolls = rolls, status = status),
            ),
        )
    }
}
