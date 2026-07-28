package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status

@Inject
@ContributesBinding(AppScope::class)
class CalculateTestSuccessCountUseCaseImpl : CalculateTestSuccessCountUseCase {

    override operator fun invoke(status: Status, rolls: List<Int>): Int {
        val successThreshold = when (status) {
            Status.Blessed -> 4
            Status.None -> 5
            Status.Cursed -> 6
        }

        return rolls.count { roll -> roll >= successThreshold }
    }
}
