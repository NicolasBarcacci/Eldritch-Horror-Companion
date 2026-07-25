package fr.meteordesign.eldritchhorrorcompanion.data.core

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core._di.AppScope
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.RandomNumberRepository
import kotlin.random.Random

@ContributesBinding(AppScope::class)
@Inject
class RandomNumberRepositoryImpl : RandomNumberRepository {

    override fun nextInt(from: Int, until: Int): Int =
        Random.nextInt(from = from, until = until)
}
