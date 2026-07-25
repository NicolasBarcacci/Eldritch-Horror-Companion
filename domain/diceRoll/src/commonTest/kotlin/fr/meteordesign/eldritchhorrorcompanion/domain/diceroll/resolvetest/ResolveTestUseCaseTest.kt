package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest

import dev.mokkery.answering.returns
import dev.mokkery.answering.sequentiallyReturns
import dev.mokkery.every
import dev.mokkery.mock
import fr.meteordesign.eldritchhorrorcompanion.domain.core.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.RandomNumberRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class ResolveTestUseCaseTest {

    private val randomNumberRepository = mock<RandomNumberRepository>()

    private val resolveTestUseCase = ResolveTestUseCase(randomNumberRepository)

    @Test
    fun `invoke returns failure when diceCount is less than 1`() {
        // Given
        val expected = Result.Failure(ResolveTestError.InvalidDiceCount)

        // When
        val actual = resolveTestUseCase(diceCount = 0, status = Status.NONE)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke succeeds when diceCount is exactly 1`() {
        // Given
        every { randomNumberRepository.nextInt(1, 7) } returns 6
        val expected = Result.Success(TestResult(rolls = listOf(6), successCount = 1))

        // When
        val actual = resolveTestUseCase(diceCount = 1, status = Status.NONE)

        // Then
        assertEquals(expected, actual)
    }


    @Test
    fun `invoke returns rolls matching the repository values in order`() {
        // Given
        val rolls = listOf(1, 2, 3)
        every { randomNumberRepository.nextInt(1, 7) } sequentiallyReturns rolls
        val expected = Result.Success(TestResult(rolls = rolls, successCount = 0))

        // When
        val actual = resolveTestUseCase(diceCount = 3, status = Status.NONE)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 5 when status is NONE`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        every { randomNumberRepository.nextInt(1, 7) } sequentiallyReturns rolls
        val expected = Result.Success(TestResult(rolls = rolls, successCount = 2))

        // When
        val actual = resolveTestUseCase(diceCount = 6, status = Status.NONE)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 4 when status is BLESSED`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        every { randomNumberRepository.nextInt(1, 7) } sequentiallyReturns rolls
        val expected = Result.Success(TestResult(rolls = rolls, successCount = 3))

        // When
        val actual = resolveTestUseCase(diceCount = 6, status = Status.BLESSED)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 6 when status is CURSED`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        every { randomNumberRepository.nextInt(1, 7) } sequentiallyReturns rolls
        val expected = Result.Success(TestResult(rolls = rolls, successCount = 1))

        // When
        val actual = resolveTestUseCase(diceCount = 6, status = Status.CURSED)

        // Then
        assertEquals(expected, actual)
    }
}
