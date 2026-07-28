package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import dev.mokkery.answering.returns
import dev.mokkery.answering.sequentiallyReturns
import dev.mokkery.every
import dev.mokkery.mock
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.core.RollD6UseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.ResolveTestError
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult
import kotlin.test.Test
import kotlin.test.assertEquals

class ResolveTestUseCaseImplTest {

    private val rollD6UseCase = mock<RollD6UseCase>()
    private val calculateTestSuccessCountUseCase = mock<CalculateTestSuccessCountUseCase>()

    private val resolveTestUseCase = ResolveTestUseCaseImpl(rollD6UseCase, calculateTestSuccessCountUseCase)

    @Test
    fun `invoke returns failure when diceCount is less than 1`() {
        // Given
        val expected = Result.Failure(
            ResolveTestError.InvalidDiceCount,
        )

        // When
        val actual = resolveTestUseCase(status = Status.None, diceCount = 0)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke succeeds when diceCount is exactly 1`() {
        // Given
        every { rollD6UseCase() } returns 6
        every { calculateTestSuccessCountUseCase(status = Status.None, rolls = listOf(6)) } returns 1
        val expected = Result.Success(
            TestResult(rolls = listOf(6), successCount = 1),
        )

        // When
        val actual = resolveTestUseCase(status = Status.None, diceCount = 1)

        // Then
        assertEquals(expected, actual)
    }


    @Test
    fun `invoke returns rolls matching the repository values in order`() {
        // Given
        val rolls = listOf(1, 2, 3)
        every { rollD6UseCase() } sequentiallyReturns rolls
        every { calculateTestSuccessCountUseCase(status = Status.None, rolls = rolls) } returns 0
        val expected = Result.Success(
            TestResult(rolls = rolls, successCount = 0),
        )

        // When
        val actual = resolveTestUseCase(status = Status.None, diceCount = 3)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 5 when status is NONE`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        every { rollD6UseCase() } sequentiallyReturns rolls
        every { calculateTestSuccessCountUseCase(status = Status.None, rolls = rolls) } returns 2
        val expected = Result.Success(
            TestResult(rolls = rolls, successCount = 2),
        )

        // When
        val actual = resolveTestUseCase(status = Status.None, diceCount = 6)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 4 when status is BLESSED`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        every { rollD6UseCase() } sequentiallyReturns rolls
        every { calculateTestSuccessCountUseCase(status = Status.Blessed, rolls = rolls) } returns 3
        val expected = Result.Success(
            TestResult(rolls = rolls, successCount = 3),
        )

        // When
        val actual = resolveTestUseCase(status = Status.Blessed, diceCount = 6)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 6 when status is CURSED`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        every { rollD6UseCase() } sequentiallyReturns rolls
        every { calculateTestSuccessCountUseCase(status = Status.Cursed, rolls = rolls) } returns 1
        val expected = Result.Success(
            TestResult(rolls = rolls, successCount = 1),
        )

        // When
        val actual = resolveTestUseCase(status = Status.Cursed, diceCount = 6)

        // Then
        assertEquals(expected, actual)
    }
}
