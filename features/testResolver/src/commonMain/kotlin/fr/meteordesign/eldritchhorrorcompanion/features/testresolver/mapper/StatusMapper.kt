package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.Configuration.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status as DomainStatus

interface StatusMapper {
    fun map(status: Status): DomainStatus
}
