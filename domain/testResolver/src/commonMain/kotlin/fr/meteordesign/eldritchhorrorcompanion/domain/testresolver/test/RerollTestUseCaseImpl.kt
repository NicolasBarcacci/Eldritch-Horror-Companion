package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.RollD6UseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult

@Inject
@ContributesBinding(AppScope::class)
class RerollTestUseCaseImpl(
    private val rollD6UseCase: RollD6UseCase,
    private val calculateTestSuccessCountUseCase: CalculateTestSuccessCountUseCase,
) : RerollTestUseCase {

    override operator fun invoke(
        status: Status,
        previousRolls: List<Int>,
        indicesToReroll: Set<Int>,
    ): TestResult {
        val newRolls = previousRolls.mapIndexed { index, roll ->
            when {
                index in indicesToReroll -> rollD6UseCase()
                else -> roll
            }
        }

        return TestResult(
            rolls = newRolls,
            successCount = calculateTestSuccessCountUseCase(status = status, rolls = newRolls),
        )
    }
}
