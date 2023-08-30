package com.aaron.cableninja.ui.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.aaron.cableninja.R
import com.aaron.cableninja.ui.viewmodels.SharedViewModel
import com.aaron.cableninja.data.getCableLoss
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.data.isNumeric
import com.aaron.cableninja.ui.dialogs.LengthDialog
import com.aaron.cableninja.ui.navigation.Screen
import com.aaron.cableninja.ui.theme.LightBlue
import com.aaron.cableninja.ui.theme.LightGreen
import com.aaron.cableninja.ui.theme.LightRed
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import kotlin.math.round


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    var freqSliderPosition by remember { mutableStateOf(sharedViewModel.currentFreq) }
    var tempSliderPosition by remember { mutableStateOf(sharedViewModel.currentTemp) }
    var startLevel by remember { mutableStateOf(sharedViewModel.currentStartLevel) }
    var editLengthDialog by remember { mutableStateOf(false) }

    // temp card for editing current card
    var editCard by remember { mutableStateOf(AttenuatorCard("", listOf(), false)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // Frequency slider
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
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(text = "${round(freqSliderPosition).toInt()} MHz")
            }
            Slider(
                value = freqSliderPosition,
                onValueChange = {
                    freqSliderPosition = it
                },
                valueRange = 12f..1218f,
                steps = 201,
                onValueChangeFinished = {
                    sharedViewModel.setFreq(freqSliderPosition)

                    if (sharedViewModel.attenuatorCardList.isNotEmpty())
                        sharedViewModel.setHasListChanged()
                }
            )
        }

        // Temp slider
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
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
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
                    sharedViewModel.setTemp(tempSliderPosition)

                    if (sharedViewModel.attenuatorCardList.isNotEmpty())
                        sharedViewModel.setHasListChanged()
                }

            )
        }

        // Starting level
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Column {
                Text(
                    text = "Starting Level: ",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "@ ${round(sharedViewModel.currentFreq).toInt()} MHz",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            OutlinedTextField(
                value = startLevel,
                onValueChange = {
                    val str = it.trim()

                    // if we manually delete all characters, update start level state
                    if (str.isEmpty() || str.isDigitsOnly()) {
                        sharedViewModel.setStartLevel(str)
                        startLevel = str
                    }
                    else if (str == "-" || isNumeric(str) || str.contains(char = '.'))
                        startLevel = str
                },
                label = { Text(text = "dBmV") },
                singleLine = true,
                // show number pad for input
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(start = 80.dp, end = 10.dp)
            )

            // only allow input that is a positive or negative whole number
            if (startLevel.isNotEmpty() && isNumeric(startLevel))
                sharedViewModel.setStartLevel(startLevel)
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
            if (sharedViewModel.hasListChanged) {
                sharedViewModel.attenuatorCardList.forEach {
                    it.setLoss(
                        getCableLoss(
                            sharedViewModel.attenuatorMap[it.name()],
                            sharedViewModel.currentFreq.toInt(),
                            it.length(),
                            sharedViewModel.currentTemp.toInt()
                        )
                    )

                }
            }

            if (sharedViewModel.attenuatorCardList.isNotEmpty()) {
                var total = 0.0

                sharedViewModel.attenuatorCardList.forEach {
                    total += it.getLoss()

                    AddAttenuatorCard(it,
                        onSwipeEdit = {
                            // save the card for editing outside the loop
                            editCard = it
                            editLengthDialog = true
                        },
                        onSwipeDelete = {
                            if (sharedViewModel.attenuatorCardList.contains(it)) {
                                sharedViewModel.setTotalAtten(
                                    sharedViewModel.totalAttenuation - it.getLoss()
                                )

                                sharedViewModel.attenuatorCardList.remove(it)
                                sharedViewModel.setHasListChanged()
                            }
                        }
                    )
                }

                sharedViewModel.setTotalAtten(total)
            } else // if no attenuators, show a message
                Text(text = "Tap to add an attenuator",
                    modifier = Modifier
                        .padding(top = 160.dp)
                        .clickable {
                            navController.navigate(route = Screen.Add.route)
                        }
                )
        }

        Divider()

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .weight(2.25f)
        ) {
            // total attenuation
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // round loss to nearest 10th
                Text(
                    text = "Total Attenuation: ",
                    modifier = Modifier.weight(2f)
                )
                if (sharedViewModel.totalAttenuation > 0)
                    Text(text = "-")

                Text(
                    text = (round(sharedViewModel.totalAttenuation * 10) / 10).toString() + " dB"
                )
            }

            // ending level
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Ending level: ",
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(2f)
                )

                // if we set a start level, do the math and display end result
                if (sharedViewModel.currentStartLevel.isNotEmpty()) {
                    val result =
                        sharedViewModel.currentStartLevel.toDouble() - sharedViewModel.totalAttenuation
                    var color: Color = MaterialTheme.colorScheme.primary

                    // figure out if all the cards are plant cards
                    var allPlant = true
                    for (card in sharedViewModel.attenuatorCardList.iterator()) {
                        if (!card.tags().contains(AttenuatorType.PLANT) || card.name() == "RG11")
                            allPlant = false
                    }

                    if (sharedViewModel.attenuatorCardList.isEmpty())
                        allPlant = false

                    // if it is not all plant, use CPE specs for coloring end level
                    if (!allPlant) {
                        // if result is between -10 and +10, set color to green
                        if ((result >= -10) && (result <= 10))
                            color = LightGreen
                        else // otherwise, set color to red
                            color = Color.Red
                    }

                    Text(
                        text = (round(result * 10) / 10).toString() + " dBmV",
                        color = color,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            // Button row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp),
            ) {
                // Clear Button
                //      click clears attenuator list
                Button(
                    onClick = {
                        sharedViewModel.attenuatorCardList.clear()
                        sharedViewModel.setTotalAtten(0.0)
                        startLevel = ""
                        sharedViewModel.setStartLevel("")
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
                //      click calls AddScreen()
                Button(
                    onClick = {
                        sharedViewModel.clearFilters()
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

        // edit length of current item in list
        if (editLengthDialog && editCard.length() > 0) {
            LengthDialog(
                label = "Edit length",
                defaultValue = editCard.length().toString(),
                onCancel = { editLengthDialog = false },
                onAdd = {
                    // TODO: is this still needed?
                    sharedViewModel.addAttenuatorLength(it.toInt())

                    // find card and edit footage
                    for (card in sharedViewModel.attenuatorCardList.iterator()) {
                        if (card.name() == editCard.name() &&
                            card.tags() == editCard.tags() &&
                            card.length() == editCard.length())
                            card.setLength(it.toInt())
                    }

                    // set state to re-draw main list
                    sharedViewModel.setHasListChanged()
                    editLengthDialog = false
                }
            )
        }
    }
}

@Composable
private fun AddAttenuatorCard(
    data: AttenuatorCard,
    onSwipeEdit: () -> Unit,
    onSwipeDelete: () -> Unit
) {
    // Edit swipe (left)
    val edit = SwipeAction(
        onSwipe = {
            onSwipeEdit()
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.baseline_edit_24),
                contentDescription = "Edit",
                tint = Color.LightGray
            )
        },
        background = LightBlue
    )

    // Delete swipe (right)
    val delete = SwipeAction(
        onSwipe = {
            onSwipeDelete()
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                contentDescription = "Delete forever",
                tint = Color.LightGray
            )
        },
        background = LightRed
    )

    // Swipeable Card
    SwipeableActionsBox(
        swipeThreshold = 130.dp,
        startActions = listOf(edit),
        endActions = listOf(delete),
        backgroundUntilSwipeThreshold = Color.Transparent
    ) {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
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
                    text = data.name(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = 15.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
                )
                // if coax, show footage in middle
                if (data.isCoax()) {
                    Text(
                        text = data.length().toString() + "'",
                        modifier = Modifier.padding(
                            start = 15.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                    )
                }

                // show attenuation on right
                Text(
                    text = (round(data.getLoss() * 10) / 10).toString() + "dB",
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 5.dp,
                        end = 15.dp
                    )
                )
            }
        }
    }
}