package mefimox.installedapps.domain.repositories

import mefimox.installedapps.domain.models.AppInfo
import mefimox.installedapps.domain.models.AppInfoShort

interface PackagesRepository {
    fun getAppsInfoShortNoIcon(): List<AppInfoShort>
    fun getAppsInfoShort(appsNoIcon: List<AppInfoShort>): List<AppInfoShort>

    fun getAppInfoNoChecksum(appInfoShort: AppInfoShort): AppInfo
    fun getAppInfo(appInfoNoChecksum: AppInfo): AppInfo
}
