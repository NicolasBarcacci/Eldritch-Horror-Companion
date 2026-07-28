package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult

interface RerollTestUseCase {
    operator fun invoke(status: Status, previousRolls: List<Int>, indicesToReroll: Set<Int>): TestResult
}
