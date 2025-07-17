package mefimox.installedapps.domain.models

import android.content.Intent
import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val versionName: String?,
    val name: String?,
    val launchIntent: Intent?,
    val sourceDir: String?,
    val checksum: String?,
    val iconDrawable: Drawable?
)
