package com.aaron.cableninja.ui.settingscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    var highSplitIsChecked by remember { mutableStateOf(false) }
    var docsis4IsChecked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 20.dp)
    ) {
        SettingToggle(
            text = "High-split Support",
            value = highSplitIsChecked,
            onChange = { highSplitIsChecked = it }
        )
        SettingToggle(
            text = "DOCSIS-4 1.8GHz Support",
            value = docsis4IsChecked,
            onChange = { docsis4IsChecked = it }
        )
    }
}

@Composable
fun SettingToggle(
    text: String,
    value: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
        Switch(
            checked = value,
            onCheckedChange = { onChange(it) }
        )
    }
}

@Composable
fun SettingField(
    text: String,
    onChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
//        TextField(
//            onValueChange = { onChange() }
//        )
    }
}