package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.mapper

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import fr.meteordesign.eldritchhorrorcompanion.domain.core.utils.AppScope
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.Configuration.Status
import fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model.Status as DomainStatus

@Inject
@ContributesBinding(AppScope::class)
class StatusMapperImpl : StatusMapper {
    override fun map(status: Status): DomainStatus =
        when (status) {
            Status.Blessed -> DomainStatus.Blessed
            Status.Cursed -> DomainStatus.Cursed
            Status.None -> DomainStatus.None
        }
}
