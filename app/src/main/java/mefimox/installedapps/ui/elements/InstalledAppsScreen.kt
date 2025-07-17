package mefimox.installedapps.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mefimox.installedapps.R
import mefimox.installedapps.domain.models.AppInfoShort
import mefimox.installedapps.ui.events.MainEvent
import mefimox.installedapps.ui.vm.MainViewModel

@Composable
fun InstalledAppsScreen(vm: MainViewModel) {
    Scaffold(
        topBar = { TopBar(stringResource(R.string.installed_apps)) }
    ) { paddingValues ->
        val apps by vm.installedApps.collectAsState()
        AppsList(apps = apps, modifier = Modifier.padding(paddingValues)) {
            vm.handleEvent(MainEvent.ChooseItem(it))
        }
    }
}

@Composable
fun AppsList(apps: List<AppInfoShort>, modifier: Modifier, onItemClick: (AppInfoShort) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(items = apps, key = { it.packageName }) {
            AppListItem(it, onItemClick = onItemClick)
            HorizontalDivider()
        }
    }
}

@Composable
fun AppListItem(item: AppInfoShort, onItemClick: (AppInfoShort) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(item) }
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(50.dp),
            contentAlignment = Alignment.Center
        ) {
            if (item.iconDrawable != null) {
                AppIcon(item.iconDrawable)
            }
        }

        Text(text = item.packageName, modifier = Modifier.weight(1f))
    }
}
