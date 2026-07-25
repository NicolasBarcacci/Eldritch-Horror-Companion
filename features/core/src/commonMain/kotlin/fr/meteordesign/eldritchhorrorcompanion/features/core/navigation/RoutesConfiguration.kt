package fr.meteordesign.eldritchhorrorcompanion.features.core.navigation

import androidx.savedstate.serialization.SavedStateConfiguration

val routesConfiguration: SavedStateConfiguration = SavedStateConfiguration {
    serializersModule =
        DiceRollNavigator.serializerModule
}
