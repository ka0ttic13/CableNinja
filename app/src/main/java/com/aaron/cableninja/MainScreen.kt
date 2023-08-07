package com.aaron.cableninja

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        var tempValue = 1000    // default 1000MHz
        var freqValue = 68      // all cable manufacturers give temp specs @ 68F

        // Create Freq Slider
        // when slider changes, we need to update attenuation List

        // Create Temp Slider
        // temp only has a significant impact on coax attenuators

        // Create List
        CreateList(navController)

        // Create Clear Button

        // Show Total Attenuation
        // updates any time an attenuator is added or removed from the List

        // Create Add button (bottom right, round "+" symbol)
        // onClick calls AddScreen()
    }
}

@Composable
private fun CreateList(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.clickable {
                navController.navigate(route = Screen.Add.route)
            },
            text = "fubar"
        )
    }
}

@Composable
fun ListCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }
}