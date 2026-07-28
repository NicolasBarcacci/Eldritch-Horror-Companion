package fr.meteordesign.eldritchhorrorcompanion.domain.core

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import kotlin.random.Random

@Inject
@ContributesBinding(AppScope::class)
class RollD6UseCaseImpl : RollD6UseCase {

    override operator fun invoke(): Int = Random.nextInt(from = 1, until = 7)
}
