package fr.meteordesign.eldritchhorrorcompanion.features.diceroll.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.DiceRollNavigator
import fr.meteordesign.eldritchhorrorcompanion.features.diceroll.DiceRollView

@Inject
class DiceRollNavigatorImpl : DiceRollNavigator {

    override fun EntryProviderScope<NavKey>.entry() {
        addEntryProvider(DiceRollNavigator.Route) {
            DiceRollView()
        }
    }


}
