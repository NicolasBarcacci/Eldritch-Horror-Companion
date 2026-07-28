package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model

import eldritchhorrorcompanion.features.core.generated.resources.Res
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_reroll_cta
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_roll_cta
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult
import kotlin.test.Test
import kotlin.test.assertEquals

class TestResolverUiModelTest {

    @Test
    fun `clearEnabled is false when there is no test result`() {
        // Given
        val expected = false

        // When
        val actual = TestResolverUiModel(testResult = null).clearEnabled

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `clearEnabled is true when there is a test result`() {
        // Given
        val testResult = TestResult(dice = emptyList(), successCount = 0)
        val expected = true

        // When
        val actual = TestResolverUiModel(testResult = testResult).clearEnabled

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `rollEnabled is true when there is no test result`() {
        // Given
        val expected = true

        // When
        val actual = TestResolverUiModel(testResult = null).rollEnabled

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `rollEnabled is false when the test result has no die selected`() {
        // Given
        val testResult = TestResult(
            dice = listOf(
                TestResult.Die(roll = 1, selected = false),
                TestResult.Die(roll = 2, selected = false),
            ),
            successCount = 0,
        )
        val expected = false

        // When
        val actual = TestResolverUiModel(testResult = testResult).rollEnabled

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `rollEnabled is true when at least one die is selected`() {
        // Given
        val testResult = TestResult(
            dice = listOf(
                TestResult.Die(roll = 1, selected = false),
                TestResult.Die(roll = 2, selected = true),
            ),
            successCount = 0,
        )
        val expected = true

        // When
        val actual = TestResolverUiModel(testResult = testResult).rollEnabled

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `rollLabel is the roll cta when there is no test result`() {
        // Given
        val expected = Res.string.test_resolver_roll_cta

        // When
        val actual = TestResolverUiModel(testResult = null).rollLabel

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `rollLabel is the reroll cta when there is a test result`() {
        // Given
        val testResult = TestResult(dice = emptyList(), successCount = 0)
        val expected = Res.string.test_resolver_reroll_cta

        // When
        val actual = TestResolverUiModel(testResult = testResult).rollLabel

        // Then
        assertEquals(expected, actual)
    }
}
