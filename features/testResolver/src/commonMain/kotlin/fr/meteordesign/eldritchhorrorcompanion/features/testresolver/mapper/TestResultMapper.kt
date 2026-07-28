package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult as DomainTestResult

interface TestResultMapper {
    fun map(testResult: DomainTestResult): TestResult
}
