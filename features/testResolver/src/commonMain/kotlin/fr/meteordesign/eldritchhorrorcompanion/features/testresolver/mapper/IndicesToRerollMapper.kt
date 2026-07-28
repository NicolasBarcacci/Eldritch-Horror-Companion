package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult

interface IndicesToRerollMapper {
    fun map(testResult: TestResult): Set<Int>
}
