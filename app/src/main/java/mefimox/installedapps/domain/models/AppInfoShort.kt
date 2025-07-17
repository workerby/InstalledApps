package mefimox.installedapps.domain.models

import android.graphics.drawable.Drawable

data class AppInfoShort(
    val packageName: String,
    val iconDrawable: Drawable?
)
