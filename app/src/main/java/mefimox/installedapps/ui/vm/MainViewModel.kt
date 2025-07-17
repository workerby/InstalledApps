package mefimox.installedapps.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mefimox.installedapps.domain.models.AppInfo
import mefimox.installedapps.domain.models.AppInfoShort
import mefimox.installedapps.domain.usecases.GetAppInfo
import mefimox.installedapps.domain.usecases.GetInstalledPackages
import mefimox.installedapps.ui.events.MainEvent
import mefimox.installedapps.ui.navigation.MainRoutes
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInstalledPackages: GetInstalledPackages,
    private val getAppInfo: GetAppInfo
) : ViewModel() {
    private val _installedApps = MutableStateFlow<List<AppInfoShort>>(emptyList())
    val installedApps = _installedApps.asStateFlow()

    private val _chosenApp = MutableStateFlow<AppInfo?>(null)
    val chosenApp = _chosenApp.asStateFlow()

    private var chooseAppJob: Job? = null

    private val _navigation = MutableSharedFlow<MainRoutes>()
    val navigation = _navigation.asSharedFlow()

    init {
        loadPackages()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ChooseItem -> chooseItem(event.appInfoShort)
            is MainEvent.Navigate -> navigate(event.route)
        }
    }

    private fun navigate(route: MainRoutes) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _navigation.emit(route)
            }
        }
    }

    private fun chooseItem(appInfoShort: AppInfoShort) {
        chooseAppJob?.cancel()
        chooseAppJob = viewModelScope.launch {
            launch {
                withContext(Dispatchers.Default) {
                    getAppInfo.invoke(appInfoShort).collect {
                        _chosenApp.value = it
                    }
                }
            }
            navigate(MainRoutes.APP_INFO)
        }
    }

    private fun loadPackages() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                getInstalledPackages().collect {
                    _installedApps.value = it
                }
            }
        }
    }
}