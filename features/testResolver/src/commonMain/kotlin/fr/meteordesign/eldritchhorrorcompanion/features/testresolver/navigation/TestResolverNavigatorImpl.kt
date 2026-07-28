package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.TestResolverNavigator
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.TestResolverNavigator.Route
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.view.TestResolverView
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Inject
@ContributesBinding(AppScope::class)
class TestResolverNavigatorImpl : TestResolverNavigator {

    override fun EntryProviderScope<NavKey>.entry() {
        addEntryProvider(Route) {
            TestResolverView()
        }
    }

    override fun serializerModule(): SerializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route::class, Route.serializer())
        }
    }
}
