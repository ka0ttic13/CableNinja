package com.aaron.cableninja.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aaron.cableninja.MainActivity.Companion.attenuatorCardList
import com.aaron.cableninja.MainActivity.Companion.manufacturerSpecsMap
import kotlin.math.round
import com.aaron.cableninja.R
import com.aaron.cableninja.model.AttenuatorCard
import com.aaron.cableninja.model.getCableLoss

@Composable
fun MainScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    //var total by remember { mutableStateOf(0.0) }                 // total attenuation
    var freqSliderPosition by remember { mutableStateOf(sharedViewModel.currentFreq)}  // default to 1219MHz
    var tempSliderPosition by remember { mutableStateOf(sharedViewModel.currentTemp)}    // default to 70F

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // Frequency slider
        // TODO: when slider changes, we need to update attenuation List and total attenuation
        Column(
            modifier = Modifier.weight(.75f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Frequency: ",
                    fontWeight = FontWeight.Bold
                )
                Text(round(freqSliderPosition).toInt().toString() + " MHz")
            }
            Slider(
                value = freqSliderPosition,
                onValueChange = {
                    freqSliderPosition = it
                },
                valueRange = 5f..1219f,
                steps = 24,
                onValueChangeFinished = {
                    Log.d("DEBUG", "MainScreen() freqSliderPosition onValueChangeFinished = $freqSliderPosition")
                    sharedViewModel.setFreq(freqSliderPosition)
                    // if there is something in our list, we need to update it for new frequency
                    if (attenuatorCardList.size > 0)
                        sharedViewModel.setHasListChanged()
                }
            )
        }

        // Temp slider
        // TODO: when slider changes, we need to update attenuation List and total attenuation
        Column(
            modifier = Modifier.weight(.75f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Temperature: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = round(tempSliderPosition).toInt().toString() + " F"
                )
            }
            Slider(
                value = tempSliderPosition,
                onValueChange = {
                    tempSliderPosition = it
                },
                valueRange = -40f..120f,
                steps = 15,
                onValueChangeFinished = {
                    Log.d("DEBUG", "MainScreen() tempSliderPosition = $tempSliderPosition")
                    sharedViewModel.setTemp(tempSliderPosition)

                    // if there is something in our list, we need to update it for new temperature
                    if (attenuatorCardList.size > 0)
                        sharedViewModel.setHasListChanged()
                }

            )
        }

        Divider(modifier = Modifier.padding(10.dp))

        // Main attenuation list
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // if the list has changed (frequency or temperature slider changed)
            // then redraw the list with current attenuation
            if (sharedViewModel.hasListChanged) {
                attenuatorCardList.forEach() {
                    val data = manufacturerSpecsMap[it.id()]

                    it.setLoss(
                        getCableLoss(
                            data,
                            sharedViewModel.currentFreq.toInt(),
                            it.footage(),
                            sharedViewModel.currentTemp.toInt()
                        )
                    )
                }
            }

            // IF there is an existing list, show it
            if (attenuatorCardList.size > 0 &&
                !sharedViewModel.clearAttenuatorList) {
                var total = 0.0

                // iterate over list and create Card for each item
                attenuatorCardList.forEach() {
                    // add attenuation of each item to total
                    total += it.getLoss()
                    AddAttenuatorCard(it)
                }

                // set total attenuation
                sharedViewModel.setTotalAtten(total)
            }
            else // if no attenuators, show a message
                Text(text = "Tap to add an attenuator",
                    modifier = Modifier
                        .padding(top = 140.dp)
                        .clickable {
                            navController.navigate(route = Screen.Add.route)
                        }
                    )
        }

        Divider()

        // Show Total Attenuation
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .weight(1.5f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // round loss to nearest 100th
                val loss = round(sharedViewModel.totalAttenuation * 100) / 100

                Text(text = "Total Attenuation: ",
                    modifier = Modifier.weight(2f))
                Text(text = loss.toString() + " dB")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp),
            ) {
                // Clear Button
                //  Clear attenuator list
                Button(
                    onClick = {
                        attenuatorCardList.clear()
                        sharedViewModel.setTotalAtten(0.0)
                        sharedViewModel.setClearListTrue()
                    },
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.weight(2f)
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_delete_forever_24),
                        contentDescription = "Clear list",
                    )
                    Text(
                        text = "Clear",
                        modifier = Modifier.padding(5.dp)
                    )
                }

                // Add Button
                //  click calls AddScreen()
                Button(
                    onClick = {
                        // on click, navigate to AddScreen
                        sharedViewModel.onFootageDismissDialog()
                        navController.navigate(route = Screen.Add.route)
                    },
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.weight(2f)
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_add_circle_24),
                        contentDescription = "Add attenuator",
                    )
                    Text(
                        text = "Add",
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AddAttenuatorCard(data: AttenuatorCard) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                // TODO edit card
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            // show attenuator ID on left
            Text(
                text = data.id(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 15.dp,
                    top = 5.dp,
                    bottom = 5.dp
                )
            )
            // if coax, show footage in middle
            if (data.iscoax()) {
                Text(
                    text = data.footage().toString() + "'",
                    modifier = Modifier.padding(
                        start = 15.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
                )
            }

            // round loss to nearest hundred
            val loss = round(data.getLoss() * 100) / 100

            // show attenuation on right
            Text(
                text = loss.toString() + "dB",
                modifier = Modifier.padding(
                    top = 5.dp,
                    bottom = 5.dp,
                    end = 15.dp
                )
            )
        }
    }
}
