package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult
import kotlin.test.Test
import kotlin.test.assertEquals

class IndicesToRerollMapperImplTest {

    private val indicesToRerollMapper = IndicesToRerollMapperImpl()

    @Test
    fun `map returns an empty set when no die is selected`() {
        // Given
        val testResult = TestResult(
            dice = listOf(
                TestResult.Die(roll = 1, selected = false),
                TestResult.Die(roll = 2, selected = false),
            ),
            successCount = 0,
        )
        val expected = emptySet<Int>()

        // When
        val actual = indicesToRerollMapper.map(testResult)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `map returns the indices of the selected dice only`() {
        // Given
        val testResult = TestResult(
            dice = listOf(
                TestResult.Die(roll = 1, selected = true),
                TestResult.Die(roll = 2, selected = false),
                TestResult.Die(roll = 3, selected = true),
            ),
            successCount = 0,
        )
        val expected = setOf(0, 2)

        // When
        val actual = indicesToRerollMapper.map(testResult)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `map returns all indices when every die is selected`() {
        // Given
        val testResult = TestResult(
            dice = listOf(
                TestResult.Die(roll = 4, selected = true),
                TestResult.Die(roll = 5, selected = true),
            ),
            successCount = 0,
        )
        val expected = setOf(0, 1)

        // When
        val actual = indicesToRerollMapper.map(testResult)

        // Then
        assertEquals(expected, actual)
    }
}
