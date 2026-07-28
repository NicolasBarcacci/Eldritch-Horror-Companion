package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test

import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateTestSuccessCountUseCaseImplTest {

    private val calculateTestSuccessCountUseCase = CalculateTestSuccessCountUseCaseImpl()

    @Test
    fun `invoke returns 0 when rolls is empty`() {
        // Given
        val expected = 0

        // When
        val actual = calculateTestSuccessCountUseCase(status = Status.None, rolls = emptyList())

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke excludes rolls below threshold`() {
        // Given
        val rolls = listOf(3, 4)
        val expected = 0

        // When
        val actual = calculateTestSuccessCountUseCase(status = Status.None, rolls = rolls)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 6 when status is CURSED`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        val expected = 1

        // When
        val actual = calculateTestSuccessCountUseCase(status = Status.Cursed, rolls = rolls)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 5 when status is NONE`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        val expected = 2

        // When
        val actual = calculateTestSuccessCountUseCase(status = Status.None, rolls = rolls)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 4 when status is BLESSED`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        val expected = 3

        // When
        val actual = calculateTestSuccessCountUseCase(status = Status.Blessed, rolls = rolls)

        // Then
        assertEquals(expected, actual)
    }
}
