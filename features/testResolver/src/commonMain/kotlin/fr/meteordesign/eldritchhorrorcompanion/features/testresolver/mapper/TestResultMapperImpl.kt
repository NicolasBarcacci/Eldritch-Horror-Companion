package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult as DomainTestResult

@Inject
@ContributesBinding(AppScope::class)
class TestResultMapperImpl : TestResultMapper {
    override fun map(testResult: DomainTestResult): TestResult =
        TestResult(
            dice = testResult.rolls.toDice(),
            successCount = testResult.successCount,
        )

    private fun List<Int>.toDice(): List<TestResult.Die> =
        map { roll -> TestResult.Die(roll = roll, selected = false) }
}
