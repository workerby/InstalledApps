package mefimox.installedapps.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield
import mefimox.installedapps.domain.repositories.PackagesRepository
import mefimox.installedapps.domain.models.AppInfoShort

class GetInstalledPackages(private val packagesRepository: PackagesRepository) {
    operator fun invoke(): Flow<List<AppInfoShort>> = flow {
        val appsNoIcon = packagesRepository.getAppsInfoShortNoIcon()
        emit(appsNoIcon)
        yield()
        val apps = packagesRepository.getAppsInfoShort(appsNoIcon)
        emit(apps)
    }
}
