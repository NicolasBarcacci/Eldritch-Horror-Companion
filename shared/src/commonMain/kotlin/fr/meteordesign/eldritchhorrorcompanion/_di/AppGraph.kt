package fr.meteordesign.eldritchhorrorcompanion._di

import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metrox.viewmodel.ViewModelGraph
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.features.core._di.FeaturesCoreGraph

@DependencyGraph(AppScope::class)
interface AppGraph : ViewModelGraph {
    val featuresCoreGraph: FeaturesCoreGraph
}
