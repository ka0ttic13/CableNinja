package com.aaron.cableninja.ui.settingscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.aaron.cableninja.ui.viewmodels.mainViewModel

/*********************************************************************************
 * SettingsScreen()
 *      Screen to show settings
 *********************************************************************************/
@Composable
fun SettingsScreen(
    navController: NavController,
    mainViewModel: mainViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "High-split Support")
        Text(text = "DOCSIS 4 1.8GHz Support")
    }
}