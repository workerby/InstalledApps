package mefimox.installedapps.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield
import mefimox.installedapps.domain.repositories.PackagesRepository
import mefimox.installedapps.domain.models.AppInfo
import mefimox.installedapps.domain.models.AppInfoShort

class GetAppInfo(private val packagesRepository: PackagesRepository) {
    operator fun invoke(appInfoShort: AppInfoShort): Flow<AppInfo> = flow {
        val appNoChecksum = packagesRepository.getAppInfoNoChecksum(appInfoShort)
        emit(appNoChecksum)
        yield()
        val app = packagesRepository.getAppInfo(appNoChecksum)
        emit(app)
    }
}
