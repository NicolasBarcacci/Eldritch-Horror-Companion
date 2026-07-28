package fr.meteordesign.eldritchhorrorcompanion.features.testresolver

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.Result
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.RerollTestUseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.ResolveTestUseCase
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.ResolveTestError
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper.IndicesToRerollMapper
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper.StatusMapper
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper.TestResultMapper
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.Configuration
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.Configuration.Status
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult
import kotlin.test.Test
import kotlin.test.assertEquals
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status as DomainStatus
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.TestResult as DomainTestResult

class TestResolverViewModelTest {

    private val resolveTestUseCase = mock<ResolveTestUseCase>()
    private val rerollTestUseCase = mock<RerollTestUseCase>()
    private val statusMapper = mock<StatusMapper>()
    private val testResultMapper = mock<TestResultMapper>()
    private val indicesToRerollMapper = mock<IndicesToRerollMapper>()

    private val viewModel = TestResolverViewModel(
        resolveTestUseCase = resolveTestUseCase,
        rerollTestUseCase = rerollTestUseCase,
        statusMapper = statusMapper,
        testResultMapper = testResultMapper,
        indicesToRerollMapper = indicesToRerollMapper,
    )

    private fun givenRolledResult() {
        every { statusMapper.map(Status.None) } returns DomainStatus.None
        every { resolveTestUseCase(status = DomainStatus.None, diceCount = 3) } returns Result.Success(domainTestResult)
        every { testResultMapper.map(domainTestResult) } returns uiTestResult
        viewModel.onRollDiceClick()
    }

    @Test
    fun `onStatusSelected updates the selected status`() {
        // Given
        val expected = TestResolverUiModel(
            configuration = Configuration(
                statuses = listOf(
                    Status.Cursed,
                    Status.None,
                    Status.Blessed
                ),
                selectedStatus = Status.Blessed,
                diceCount = 3,
            ),
        )

        // When
        viewModel.onStatusSelected(Status.Blessed)
        val actual = viewModel.uiModelFlow.value

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onStatusSelected clears the previous test result and re-enables roll`() {
        // Given
        givenRolledResult()
        val expected = TestResolverUiModel(
            configuration = Configuration(
                statuses = listOf(
                    Status.Cursed,
                    Status.None,
                    Status.Blessed
                ),
                selectedStatus = Status.Cursed,
                diceCount = 3,
            ),
        )

        // When
        viewModel.onStatusSelected(Status.Cursed)
        val actual = viewModel.uiModelFlow.value

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onIncrementDiceCount increases the dice count by one`() {
        // Given
        val expected = 4

        // When
        viewModel.onIncrementDiceCount()
        val actual = viewModel.uiModelFlow.value.configuration.diceCount

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onIncrementDiceCount does not exceed the maximum of 20`() {
        // Given
        val expected = 20

        // When
        repeat(30) { viewModel.onIncrementDiceCount() }
        val actual = viewModel.uiModelFlow.value.configuration.diceCount

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onDecrementDiceCount decreases the dice count by one`() {
        // Given
        val expected = 2

        // When
        viewModel.onDecrementDiceCount()
        val actual = viewModel.uiModelFlow.value.configuration.diceCount

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onDecrementDiceCount does not go below the minimum of 1`() {
        // Given
        val expected = 1

        // When
        repeat(10) { viewModel.onDecrementDiceCount() }
        val actual = viewModel.uiModelFlow.value.configuration.diceCount

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onDieClick selects the die at the given index`() {
        // Given
        givenRolledResult()
        val expected = listOf(
            TestResult.Die(roll = 4, selected = true),
            TestResult.Die(roll = 5, selected = false),
        )

        // When
        viewModel.onDieClick(0)
        val actual = viewModel.uiModelFlow.value.testResult?.dice

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onDieClick enables roll once a die is selected`() {
        // Given
        givenRolledResult()
        val expected = true

        // When
        viewModel.onDieClick(0)
        val actual = viewModel.uiModelFlow.value.rollEnabled

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onDieClick deselects an already selected die`() {
        // Given
        givenRolledResult()
        viewModel.onDieClick(0)
        val expected = listOf(
            TestResult.Die(roll = 4, selected = false),
            TestResult.Die(roll = 5, selected = false),
        )

        // When
        viewModel.onDieClick(0)
        val actual = viewModel.uiModelFlow.value.testResult?.dice

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onDieClick does nothing when there is no test result yet`() {
        // Given
        val expected = viewModel.uiModelFlow.value

        // When
        viewModel.onDieClick(0)
        val actual = viewModel.uiModelFlow.value

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onClearClick clears the test result and re-enables roll`() {
        // Given
        givenRolledResult()
        val expected = TestResolverUiModel(
            configuration = Configuration(
                statuses = listOf(
                    Status.Cursed,
                    Status.None,
                    Status.Blessed
                ),
                selectedStatus = Status.None,
                diceCount = 3,
            ),
        )

        // When
        viewModel.onClearClick()
        val actual = viewModel.uiModelFlow.value

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onRollDiceClick resolves a test and updates the ui model on success`() {
        // Given
        every { statusMapper.map(Status.None) } returns DomainStatus.None
        every { resolveTestUseCase(status = DomainStatus.None, diceCount = 3) } returns Result.Success(domainTestResult)
        every { testResultMapper.map(domainTestResult) } returns uiTestResult
        val expected = TestResolverUiModel(testResult = uiTestResult)

        // When
        viewModel.onRollDiceClick()
        val actual = viewModel.uiModelFlow.value

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onRollDiceClick leaves the ui model unchanged when resolving fails`() {
        // Given
        every { statusMapper.map(Status.None) } returns DomainStatus.None
        every { resolveTestUseCase(status = DomainStatus.None, diceCount = 3) } returns
            Result.Failure(ResolveTestError.InvalidDiceCount)
        val expected = TestResolverUiModel()

        // When
        viewModel.onRollDiceClick()
        val actual = viewModel.uiModelFlow.value

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `onRollDiceClick rerolls the previously selected dice`() {
        // Given
        givenRolledResult()
        viewModel.onDieClick(0)
        val selectedResult = TestResult(
            dice = listOf(
                TestResult.Die(roll = 4, selected = true),
                TestResult.Die(roll = 5, selected = false),
            ),
            successCount = 1,
        )
        val rerolledDomainResult = DomainTestResult(rolls = listOf(6, 5), successCount = 2)
        val rerolledUiResult = TestResult(
            dice = listOf(
                TestResult.Die(roll = 6, selected = false),
                TestResult.Die(roll = 5, selected = false),
            ),
            successCount = 2,
        )
        every { indicesToRerollMapper.map(selectedResult) } returns setOf(0)
        every {
            rerollTestUseCase(status = DomainStatus.None, previousRolls = listOf(4, 5), indicesToReroll = setOf(0))
        } returns rerolledDomainResult
        every { testResultMapper.map(rerolledDomainResult) } returns rerolledUiResult
        val expected = TestResolverUiModel(testResult = rerolledUiResult)

        // When
        viewModel.onRollDiceClick()
        val actual = viewModel.uiModelFlow.value

        // Then
        assertEquals(expected, actual)
    }

    private companion object {
        val domainTestResult = DomainTestResult(rolls = listOf(4, 5), successCount = 1)
        val uiTestResult = TestResult(
            dice = listOf(
                TestResult.Die(roll = 4, selected = false),
                TestResult.Die(roll = 5, selected = false),
            ),
            successCount = 1,
        )
    }
}
