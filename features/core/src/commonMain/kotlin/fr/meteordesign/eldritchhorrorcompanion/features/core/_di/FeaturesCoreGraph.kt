package fr.meteordesign.eldritchhorrorcompanion.features.core._di

import androidx.savedstate.serialization.SavedStateConfiguration
import dev.zacsweers.metro.GraphExtension
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.DiceRollNavigator

@GraphExtension
interface FeaturesCoreGraph {

    val diceRollNavigator: DiceRollNavigator

    val routesConfiguration: SavedStateConfiguration
        get() = SavedStateConfiguration {
            serializersModule =
                diceRollNavigator.serializerModule()
        }
}
