package fr.meteordesign.eldritchhorrorcompanion.domain.core

import dev.zacsweers.metro.Inject
import kotlin.random.Random

@Inject
open class RollD6UseCase {

    open operator fun invoke(): Int = Random.nextInt(from = 1, until = 7)
}
