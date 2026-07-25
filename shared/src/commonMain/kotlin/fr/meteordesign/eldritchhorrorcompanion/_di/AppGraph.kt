package fr.meteordesign.eldritchhorrorcompanion._di

import dev.zacsweers.metro.DependencyGraph
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll._di.FeaturesDiceRollGraph

@DependencyGraph
interface AppGraph {
    val featuresDiceRollGraph: FeaturesDiceRollGraph
}
