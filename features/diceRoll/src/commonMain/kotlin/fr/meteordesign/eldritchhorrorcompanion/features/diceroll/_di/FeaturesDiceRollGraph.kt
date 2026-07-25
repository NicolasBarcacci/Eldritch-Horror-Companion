package fr.meteordesign.eldritchhorrorcompanion.features.diceroll._di

import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.GraphExtension
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.DiceRollNavigator
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.navigation.DiceRollNavigatorImpl

@GraphExtension
interface FeaturesDiceRollGraph {

    val diceRollNavigator: DiceRollNavigator

    @Binds
    val DiceRollNavigatorImpl.bind: DiceRollNavigator
}
