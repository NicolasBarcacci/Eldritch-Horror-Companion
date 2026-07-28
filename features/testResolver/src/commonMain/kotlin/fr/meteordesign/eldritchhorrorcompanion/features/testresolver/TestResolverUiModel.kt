package fr.meteordesign.eldritchhorrorcompanion.features.testresolver

import eldritchhorrorcompanion.features.core.generated.resources.Res
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_roll_cta
import eldritchhorrorcompanion.features.core.generated.resources.status_blessed
import eldritchhorrorcompanion.features.core.generated.resources.status_cursed
import eldritchhorrorcompanion.features.core.generated.resources.status_none
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.TestResolverUiModel.Configuration.Status.Blessed
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.TestResolverUiModel.Configuration.Status.Cursed
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.TestResolverUiModel.Configuration.Status.None
import org.jetbrains.compose.resources.StringResource

data class TestResolverUiModel(
    val configuration: Configuration = Configuration(
        statuses = listOf(Cursed, None, Blessed),
        selectedStatus = None,
        diceCount = 3,
    ),
    val testResult: TestResult? = null,
    val rollEnabled: Boolean = true,
    val rollLabel: StringResource = Res.string.test_resolver_roll_cta,
) {
    val clearEnabled: Boolean
        get() = testResult != null

    data class Configuration(
        val statuses: List<Status>,
        val selectedStatus: Status,
        val diceCount: Int,
    ) {
        sealed interface Status {
            val label: StringResource

            data object Cursed : Status {
                override val label: StringResource = Res.string.status_cursed
            }

            data object None : Status {
                override val label: StringResource = Res.string.status_none
            }

            data object Blessed : Status {
                override val label: StringResource = Res.string.status_blessed
            }
        }
    }

    data class TestResult(
        val dice: List<Die>,
        val successCount: Int,
    ) {
        data class Die(
            val roll: Int,
            val selected: Boolean,
        )
    }
}
