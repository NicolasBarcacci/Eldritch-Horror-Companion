package fr.meteordesign.eldritchhorrorcompanion.features.diceroll.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core._di.AppScope
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.DiceRollNavigator
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.DiceRollNavigator.Route
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.view.DiceRollView
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@ContributesBinding(AppScope::class)
@Inject
class DiceRollNavigatorImpl : DiceRollNavigator {

    override fun EntryProviderScope<NavKey>.entry() {
        addEntryProvider(Route) {
            DiceRollView()
        }
    }

    override fun serializerModule(): SerializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route::class, Route.serializer())
        }
    }
}
