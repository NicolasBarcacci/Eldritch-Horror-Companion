package fr.meteordesign.eldritchhorrorcompanion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metro.createGraph
import fr.meteordesign.eldritchhorrorcompanion._di.AppGraph
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.theme.EhcTheme
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.DiceRollNavigator
import fr.meteordesign.eldritchhorrorcompanion.features.core.navigation.routesConfiguration

@Composable
@Preview
fun App() {
    EhcTheme {
        val appGraph = remember { createGraph<AppGraph>() }
        val backStack = rememberNavBackStack(routesConfiguration, DiceRollNavigator.Route)

        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                with(appGraph.featuresDiceRollGraph.diceRollNavigator) { entry() }
            },
        )
    }
}
