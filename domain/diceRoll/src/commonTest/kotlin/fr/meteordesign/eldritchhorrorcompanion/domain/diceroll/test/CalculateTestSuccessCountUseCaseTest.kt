package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test

import fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model.Status
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateTestSuccessCountUseCaseTest {

    private val calculateTestSuccessCountUseCase = CalculateTestSuccessCountUseCase()

    @Test
    fun `invoke returns 0 when rolls is empty`() {
        // Given
        val expected = 0

        // When
        val actual = calculateTestSuccessCountUseCase(rolls = emptyList(), status = Status.NONE)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke excludes rolls below threshold`() {
        // Given
        val rolls = listOf(3, 4)
        val expected = 0

        // When
        val actual = calculateTestSuccessCountUseCase(rolls = rolls, status = Status.CURSED)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 6 when status is CURSED`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        val expected = 1

        // When
        val actual = calculateTestSuccessCountUseCase(rolls = rolls, status = Status.CURSED)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 5 when status is NONE`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        val expected = 2

        // When
        val actual = calculateTestSuccessCountUseCase(rolls = rolls, status = Status.NONE)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `invoke counts successes with threshold 4 when status is BLESSED`() {
        // Given
        val rolls = listOf(1, 2, 3, 4, 5, 6)
        val expected = 3

        // When
        val actual = calculateTestSuccessCountUseCase(rolls = rolls, status = Status.BLESSED)

        // Then
        assertEquals(expected, actual)
    }
}
