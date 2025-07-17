package mefimox.installedapps.ui.events

import mefimox.installedapps.domain.models.AppInfoShort
import mefimox.installedapps.ui.navigation.MainRoutes

sealed class MainEvent {
    data class ChooseItem(val appInfoShort: AppInfoShort): MainEvent()
    data class Navigate(val route: MainRoutes): MainEvent()
}