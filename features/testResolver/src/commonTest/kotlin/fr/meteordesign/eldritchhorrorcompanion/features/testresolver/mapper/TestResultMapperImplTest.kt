package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult
import kotlin.test.Test
import kotlin.test.assertEquals
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult as DomainTestResult

class TestResultMapperImplTest {

    private val testResultMapper = TestResultMapperImpl()

    @Test
    fun `map converts rolls to unselected dice and keeps the success count`() {
        // Given
        val domainTestResult = DomainTestResult(rolls = listOf(2, 5, 6), successCount = 2)
        val expected = TestResult(
            dice = listOf(
                TestResult.Die(roll = 2, selected = false),
                TestResult.Die(roll = 5, selected = false),
                TestResult.Die(roll = 6, selected = false),
            ),
            successCount = 2,
        )

        // When
        val actual = testResultMapper.map(domainTestResult)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `map returns an empty dice list when rolls is empty`() {
        // Given
        val domainTestResult = DomainTestResult(rolls = emptyList(), successCount = 0)
        val expected = TestResult(dice = emptyList(), successCount = 0)

        // When
        val actual = testResultMapper.map(domainTestResult)

        // Then
        assertEquals(expected, actual)
    }
}
