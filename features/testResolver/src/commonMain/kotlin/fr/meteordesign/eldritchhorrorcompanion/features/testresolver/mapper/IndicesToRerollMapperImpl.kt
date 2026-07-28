package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult

@Inject
@ContributesBinding(AppScope::class)
class IndicesToRerollMapperImpl : IndicesToRerollMapper {
    override fun map(testResult: TestResult): Set<Int> =
        testResult.dice.withIndex()
            .filter { (_, die) -> die.selected }
            .map { (index, _) -> index }
            .toSet()
}
