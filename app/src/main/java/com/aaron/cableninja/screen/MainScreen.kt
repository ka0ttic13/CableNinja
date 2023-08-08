package com.aaron.cableninja.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    val attenuatorCard = sharedViewModel.card

    // if first time running, go straight to AddScreen
//    if (attenuatorCard == null) {
//        AddScreen(navController, sharedViewModel)
//        return
//    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // TODO Create Freq Slider
        // when slider changes, we need to update attenuation List

        // TODO Create Temp Slider
        // temp only has a significant impact on coax attenuators

        // IF there is an existing list, show it
        if (attenuatorCard != null) {
            // TODO Create List

            // TODO LazyColumn
            //items(  10_000) {
            //    Text(text = "fubar $it")
            //}
        }

        // TODO Create Clear Button

        // TODO Show Total Attenuation
        // TODO updates any time an attenuator is added or removed from the List

        // Add Button
        // click calls AddScreen()
        // TODO create round "+" button on bottom right
        Text(
            modifier = Modifier
                // on click, navigate to AddScreen
                .clickable { navController.navigate(route = Screen.Add.route) },
            text = "Add Attenuator"
        )
    }
}