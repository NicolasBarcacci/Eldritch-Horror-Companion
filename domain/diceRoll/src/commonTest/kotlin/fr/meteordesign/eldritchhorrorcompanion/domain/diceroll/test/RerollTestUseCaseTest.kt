package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test

import dev.mokkery.answering.returns
import dev.mokkery.answering.sequentiallyReturns
import dev.mokkery.every
import dev.mokkery.mock
import fr.meteordesign.eldritchhorrorcompanion.domain.core.RollD6UseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.TestResult
import kotlin.test.Test
import kotlin.test.assertEquals

class RerollTestUseCaseTest {

    private val rollD6UseCase = mock<RollD6UseCase>()
    private val calculateTestSuccessCountUseCase = mock<CalculateTestSuccessCountUseCase>()

    private val rerollTestUseCase = RerollTestUseCase(rollD6UseCase, calculateTestSuccessCountUseCase)

    @Test
    fun `invoke keeps previous rolls when no index is selected`() {
        // Given
        val previousRolls = listOf(1, 2, 3)
        every { calculateTestSuccessCountUseCase(rolls = previousRolls, status = Status.NONE) } returns 0
        val expected = TestResult(rolls = previousRolls, successCount = 0)

        // When
        val actual = rerollTestUseCase(
            previousRolls = previousRolls,
            indicesToReroll = emptySet(),
            status = Status.NONE,
        )

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke keeps previous rolls unchanged when selected index is negative`() {
        // Given
        val previousRolls = listOf(1, 2, 3)
        every { calculateTestSuccessCountUseCase(rolls = previousRolls, status = Status.NONE) } returns 0
        val expected = TestResult(rolls = previousRolls, successCount = 0)

        // When
        val actual = rerollTestUseCase(
            previousRolls = previousRolls,
            indicesToReroll = setOf(-1),
            status = Status.NONE,
        )

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke keeps previous rolls unchanged when selected index exceeds list size`() {
        // Given
        val previousRolls = listOf(1, 2, 3)
        every { calculateTestSuccessCountUseCase(rolls = previousRolls, status = Status.NONE) } returns 0
        val expected = TestResult(rolls = previousRolls, successCount = 0)

        // When
        val actual = rerollTestUseCase(
            previousRolls = previousRolls,
            indicesToReroll = setOf(3),
            status = Status.NONE,
        )

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke rerolls only the selected indices`() {
        // Given
        val previousRolls = listOf(1, 2, 3)
        val rolls = listOf(1, 6, 3)
        every { rollD6UseCase() } returns 6
        every { calculateTestSuccessCountUseCase(rolls = rolls, status = Status.NONE) } returns 1
        val expected = TestResult(rolls = rolls, successCount = 1)

        // When
        val actual = rerollTestUseCase(
            previousRolls = previousRolls,
            indicesToReroll = setOf(1),
            status = Status.NONE,
        )

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke rerolls all indices in order when all are selected`() {
        // Given
        val previousRolls = listOf(1, 2, 3)
        val newRolls = listOf(4, 5, 6)
        every { rollD6UseCase() } sequentiallyReturns newRolls
        every { calculateTestSuccessCountUseCase(rolls = newRolls, status = Status.NONE) } returns 2
        val expected = TestResult(rolls = newRolls, successCount = 2)

        // When
        val actual = rerollTestUseCase(
            previousRolls = previousRolls,
            indicesToReroll = setOf(0, 1, 2),
            status = Status.NONE,
        )

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke forwards status to the success count calculation`() {
        // Given
        val previousRolls = listOf(1, 2, 3)
        every { calculateTestSuccessCountUseCase(rolls = previousRolls, status = Status.BLESSED) } returns 3
        val expected = TestResult(rolls = previousRolls, successCount = 3)

        // When
        val actual = rerollTestUseCase(
            previousRolls = previousRolls,
            indicesToReroll = emptySet(),
            status = Status.BLESSED,
        )

        // Then
        assertEquals(expected, actual)
    }
}
