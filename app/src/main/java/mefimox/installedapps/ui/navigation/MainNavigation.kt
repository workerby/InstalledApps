package mefimox.installedapps.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import mefimox.installedapps.ui.elements.AppInfoScreen
import mefimox.installedapps.ui.elements.InstalledAppsScreen
import mefimox.installedapps.ui.vm.MainViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val vm: MainViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = MainRoutes.INSTALLED_APPS.route) {
        composable(route = MainRoutes.INSTALLED_APPS.route) {
            InstalledAppsScreen(vm)
        }
        composable(route = MainRoutes.APP_INFO.route) {
            AppInfoScreen(vm)
        }
    }

    LaunchedEffect(Unit) {
        launch {
            vm.navigation.collect {
                navController.navigate(it.route)
            }
        }
    }
}