package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status

@Inject
open class CalculateTestSuccessCountUseCase {

    open operator fun invoke(rolls: List<Int>, status: Status): Int {
        val successThreshold = when (status) {
            Status.BLESSED -> 4
            Status.NONE -> 5
            Status.CURSED -> 6
        }

        return rolls.count { roll -> roll >= successThreshold }
    }
}
