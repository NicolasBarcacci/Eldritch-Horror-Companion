package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model

import eldritchhorrorcompanion.features.core.generated.resources.Res
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_reroll_cta
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_roll_cta
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.text.EhcTextValue

data class TestResolverUiModel(
    val configuration: Configuration = Configuration(
        statuses = listOf(
            Configuration.Status.Cursed,
            Configuration.Status.None,
            Configuration.Status.Blessed
        ),
        selectedStatus = Configuration.Status.None,
        diceCount = 3,
    ),
    val testResult: TestResult? = null,
) {
    val clearEnabled: Boolean
        get() = testResult != null

    val rollEnabled: Boolean
        get() = testResult?.dice?.any { it.selected } ?: true

    val rollLabel: EhcTextValue
        get() = when (testResult) {
            null -> EhcTextValue.StringRes(Res.string.test_resolver_roll_cta)
            else -> EhcTextValue.StringRes(Res.string.test_resolver_reroll_cta)
        }

    data class Configuration(
        val statuses: List<Status>,
        val selectedStatus: Status,
        val diceCount: Int,
    ) {
        enum class Status {
            Blessed,
            Cursed,
            None,
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
