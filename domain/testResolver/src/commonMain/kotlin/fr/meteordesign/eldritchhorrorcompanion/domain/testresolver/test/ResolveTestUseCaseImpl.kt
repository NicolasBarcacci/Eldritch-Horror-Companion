package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.RollD6UseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.ResolveTestError
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult

@Inject
@ContributesBinding(AppScope::class)
class ResolveTestUseCaseImpl(
    private val rollD6UseCase: RollD6UseCase,
    private val calculateTestSuccessCountUseCase: CalculateTestSuccessCountUseCase,
) : ResolveTestUseCase {

    override operator fun invoke(
        status: Status,
        diceCount: Int,
    ): Result<TestResult, ResolveTestError> {
        if (diceCount < 1) {
            return Result.Failure(ResolveTestError.InvalidDiceCount)
        }

        val rolls = List(diceCount) { rollD6UseCase() }

        return Result.Success(
            TestResult(
                rolls = rolls,
                successCount = calculateTestSuccessCountUseCase(status = status, rolls = rolls),
            ),
        )
    }
}
