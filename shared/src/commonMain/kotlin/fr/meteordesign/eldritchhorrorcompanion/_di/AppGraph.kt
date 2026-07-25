package fr.meteordesign.eldritchhorrorcompanion._di

import dev.zacsweers.metro.DependencyGraph
import fr.meteordesign.eldritchhorrorcompanion.domain.core._di.AppScope
import fr.meteordesign.eldritchhorrorcompanion.features.core._di.FeaturesCoreGraph

@DependencyGraph(AppScope::class)
interface AppGraph {
    val featuresCoreGraph: FeaturesCoreGraph
}
