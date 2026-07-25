package fr.meteordesign.eldritchhorrorcompanion.features.diceroll

import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest.Status

data class DiceRollUiModel(
    val status: Status = Status.NONE,
    val diceCount: Int = 1,
    val testResult: TestResult? = null,
) {
    data class TestResult(
        val rolls: List<Int>,
        val successCount: Int,
    )
}
