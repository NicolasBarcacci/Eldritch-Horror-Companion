package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status

interface CalculateTestSuccessCountUseCase {
    operator fun invoke(status: Status, rolls: List<Int>): Int
}
