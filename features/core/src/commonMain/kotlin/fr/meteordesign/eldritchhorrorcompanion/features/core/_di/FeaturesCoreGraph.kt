package fr.meteordesign.eldritchhorrorcompanion.features.core._di

import androidx.savedstate.serialization.SavedStateConfiguration
import dev.zacsweers.metro.GraphExtension
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.TestResolverNavigator

@GraphExtension
interface FeaturesCoreGraph {

    val testResolverNavigator: TestResolverNavigator

    val routesConfiguration: SavedStateConfiguration
        get() = SavedStateConfiguration {
            serializersModule =
                testResolverNavigator.serializerModule()
        }
}
