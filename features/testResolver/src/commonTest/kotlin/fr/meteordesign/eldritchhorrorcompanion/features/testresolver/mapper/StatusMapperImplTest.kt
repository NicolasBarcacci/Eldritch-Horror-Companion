package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.Configuration.Status
import kotlin.test.Test
import kotlin.test.assertEquals
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status as DomainStatus

class StatusMapperImplTest {

    private val statusMapper = StatusMapperImpl()

    @Test
    fun `map returns domain Cursed when status is Cursed`() {
        // Given
        val expected = DomainStatus.Cursed

        // When
        val actual = statusMapper.map(Status.Cursed)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `map returns domain None when status is None`() {
        // Given
        val expected = DomainStatus.None

        // When
        val actual = statusMapper.map(Status.None)

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `map returns domain Blessed when status is Blessed`() {
        // Given
        val expected = DomainStatus.Blessed

        // When
        val actual = statusMapper.map(Status.Blessed)

        // Then
        assertEquals(expected, actual)
    }
}
