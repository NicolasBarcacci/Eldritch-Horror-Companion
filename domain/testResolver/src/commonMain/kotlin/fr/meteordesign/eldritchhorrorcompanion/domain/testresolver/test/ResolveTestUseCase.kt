package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.ResolveTestError
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult

interface ResolveTestUseCase {
    operator fun invoke(status: Status, diceCount: Int): Result<TestResult, ResolveTestError>
}
