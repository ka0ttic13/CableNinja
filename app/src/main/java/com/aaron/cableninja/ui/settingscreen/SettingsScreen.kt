package com.aaron.cableninja.ui.settingscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.aaron.cableninja.ui.homescreen.RX_HIGH_THRESHOLD
import com.aaron.cableninja.ui.homescreen.RX_LOW_THRESHOLD
import com.aaron.cableninja.ui.homescreen.TX_HIGH_THRESHOLD
import com.aaron.cableninja.ui.homescreen.TX_LOW_THRESHOLD
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
    var rxLowThreshold by remember { mutableStateOf(RX_LOW_THRESHOLD) }
    var rxHighThreshold by remember { mutableStateOf(RX_HIGH_THRESHOLD) }
    var txLowThreshold by remember { mutableStateOf(TX_LOW_THRESHOLD) }
    var txHighThreshold by remember { mutableStateOf(TX_HIGH_THRESHOLD) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 20.dp)
    ) {
        // High split support
        SettingToggle(
            text = "High-split Support",
            value = highSplitIsChecked,
            onChange = { highSplitIsChecked = it }
        )
        // DOCSIS 4 - 1.8GHz support
        SettingToggle(
            text = "DOCSIS-4 1.8GHz Support",
            value = docsis4IsChecked,
            onChange = { docsis4IsChecked = it }
        )
        // RX_LOW_THRESHOLD
        SettingField(
            label = "Rx Low Threshold",
            value = RX_LOW_THRESHOLD.toString(),
            onChange = {
                rxLowThreshold = it.toInt()
            }
        )
        // RX_HIGH_THRESHOLD
        SettingField(
            label = "Rx High Threshold",
            value = RX_HIGH_THRESHOLD.toString(),
            onChange = {
                rxHighThreshold = it.toInt()
            }
        )
        // TX_LOW_THRESHOLD
        SettingField(
            label = "Tx Low Threshold",
            value = TX_LOW_THRESHOLD.toString(),
            onChange = {
                txLowThreshold = it.toInt()
            }
        )
        // TX_HIGH_THRESHOLD
        SettingField(
            label = "Tx High Threshold",
            value = TX_HIGH_THRESHOLD.toString(),
            onChange = {
                txHighThreshold = it.toInt()
            }
        )
    }
}

/****************************************************************************
 * SettingToggle()
 *      Setting that uses toggle Switch for user input
 ****************************************************************************/

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

/****************************************************************************
 * SettingField()
 *      Setting that uses TextField for user input
 ****************************************************************************/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingField(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    val text by remember { mutableStateOf(value) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(80.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label,
            modifier = Modifier.weight(.75f)
        )
        TextField(
            value = value,
            onValueChange = { onChange(it) },
            modifier = Modifier.weight(.25f)
        )
    }
}