package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test

import dev.zacsweers.metro.Inject
import kotlin.random.Random

@Inject
class ResolveTestUseCase {

    operator fun invoke(
        diceCount: Int,
        status: Status,
    ): TestResult {
        val successThreshold = when (status) {
            Status.BLESSED -> 4
            Status.NONE -> 5
            Status.CURSED -> 6
        }

        val rolls = List(diceCount) { Random.nextInt(from = 1, until = 7) }

        return TestResult(
            rolls = rolls,
            successCount = rolls.count { roll -> roll >= successThreshold },
        )
    }
}
