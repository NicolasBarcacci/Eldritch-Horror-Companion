package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest

import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.RandomNumberRepository

@Inject
class ResolveTestUseCase(
    private val randomNumberRepository: RandomNumberRepository,
) {

    operator fun invoke(
        diceCount: Int,
        status: Status,
    ): Result<TestResult, ResolveTestError> {
        if (diceCount < 1) {
            return Result.Failure(ResolveTestError.InvalidDiceCount)
        }

        val successThreshold = when (status) {
            Status.BLESSED -> 4
            Status.NONE -> 5
            Status.CURSED -> 6
        }

        val rolls = List(diceCount) { randomNumberRepository.nextInt(from = 1, until = 7) }

        return Result.Success(
            TestResult(
                rolls = rolls,
                successCount = rolls.count { roll -> roll >= successThreshold },
            ),
        )
    }
}
