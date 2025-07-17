package mefimox.installedapps.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mefimox.installedapps.domain.models.AppInfo
import mefimox.installedapps.R
import mefimox.installedapps.ui.events.MainEvent
import mefimox.installedapps.ui.navigation.MainRoutes
import mefimox.installedapps.ui.vm.MainViewModel

@Composable
fun AppInfoScreen(vm: MainViewModel) {
    Scaffold(
        topBar = { TopBar(stringResource(R.string.app_info)) }
    ) { paddingValues ->
        val appInfoState = vm.chosenApp.collectAsState()
        val appInfo = appInfoState.value
        if (appInfo == null) {
            MainEvent.Navigate(MainRoutes.INSTALLED_APPS)
            vm.handleEvent(MainEvent.Navigate(MainRoutes.INSTALLED_APPS))
        } else {
            AppInfoContent(
                appInfo = appInfo,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun AppInfoContent(appInfo: AppInfo, modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
    ) {
        if (appInfo.iconDrawable != null) {
            AppIcon(
                drawable = appInfo.iconDrawable,
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        DataRow(stringResource(R.string.name), appInfo.name ?: "-")
        DataRow(stringResource(R.string.version), appInfo.versionName ?: "-")
        DataRow(stringResource(R.string.app_package), appInfo.packageName)
        DataRow(stringResource(R.string.checksum), appInfo.checksum ?: "...")

        if (appInfo.launchIntent == null) {
            Text(text = stringResource(R.string.cant_open_app))
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
            ) {
                val context = LocalContext.current
                OpenAppButton {
                    context.startActivity(appInfo.launchIntent)
                }
            }
        }

    }
}

@Composable
fun DataRow(key: String, value: String) {
    Text(text = "$key: $value")
}

@Composable
fun OpenAppButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(2.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.open),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp
        )
    }
}
