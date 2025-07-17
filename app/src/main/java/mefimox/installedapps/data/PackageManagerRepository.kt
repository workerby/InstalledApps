package mefimox.installedapps.data

import android.content.pm.PackageManager
import android.util.Log
import mefimox.installedapps.domain.models.AppInfo
import mefimox.installedapps.domain.models.AppInfoShort
import mefimox.installedapps.domain.repositories.PackagesRepository
import java.io.File
import java.security.MessageDigest
import kotlin.String

class PackageManagerRepository(
    private val packageManager: PackageManager,
    private val checksumAlgorithm: String = "SHA-256"
) : PackagesRepository {
    override fun getAppsInfoShortNoIcon(): List<AppInfoShort> {
        return packageManager
            .getInstalledPackages(PackageManager.GET_META_DATA)
            .map { AppInfoShort(packageName = it.packageName, iconDrawable = null) }
    }

    override fun getAppsInfoShort(appsNoIcon: List<AppInfoShort>): List<AppInfoShort> {
        return appsNoIcon.map {
            it.copy(
                iconDrawable = packageManager.getApplicationIcon(it.packageName)
            )
        }
    }

    override fun getAppInfoNoChecksum(appInfoShort: AppInfoShort): AppInfo {
        val packageName = appInfoShort.packageName
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA)
        val appName = packageInfo.applicationInfo?.let { info ->
            packageManager.getApplicationLabel(info)
        }.toString()
        val appIcon = appInfoShort.iconDrawable ?: packageManager.getApplicationIcon(packageName)

        return AppInfo(
            packageName = packageName,
            versionName = packageInfo.versionName ?: "-",
            name = appName,
            launchIntent = packageManager.getLaunchIntentForPackage(packageName),
            sourceDir = packageInfo.applicationInfo?.sourceDir,
            checksum = "...",
            iconDrawable = appIcon
        )
    }

    override fun getAppInfo(appInfoNoChecksum: AppInfo): AppInfo {
        return if (appInfoNoChecksum.sourceDir != null) {
            appInfoNoChecksum.copy(checksum = calculateApkChecksum(appInfoNoChecksum.sourceDir))
        } else {
            appInfoNoChecksum
        }
    }

    private val messageDigest = MessageDigest.getInstance(checksumAlgorithm)

    private fun calculateApkChecksum(sourceDir: String): String {
        return try {
            val file = File(sourceDir)
            file.inputStream().use { fis ->
                val buffer = ByteArray(1024)
                var data: Int
                while (fis.read(buffer).also { data = it } != -1) {
                    messageDigest.update(buffer, 0, data)
                }
            }
            messageDigest.digest().joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            Log.e(this::class.toString(), e.toString())
            "-"
        }
    }


}