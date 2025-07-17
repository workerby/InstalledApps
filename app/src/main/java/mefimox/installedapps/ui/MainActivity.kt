package mefimox.installedapps.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import mefimox.installedapps.ui.navigation.MainNavigation
import mefimox.installedapps.ui.theme.InstalledAppsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InstalledAppsTheme {
                MainNavigation()
            }
        }
    }
}