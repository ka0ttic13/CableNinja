package com.aaron.cableninja.ui.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.aaron.cableninja.ui.viewmodels.mainViewModel
import com.aaron.cableninja.data.getCableLoss
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.data.isNumeric
import com.aaron.cableninja.ui.dialogs.NumericDialog
import com.aaron.cableninja.ui.navigation.BottomBarScreen
import com.aaron.cableninja.ui.theme.LightBlue
import com.aaron.cableninja.ui.theme.LightGreen
import com.aaron.cableninja.ui.theme.LightRed
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import kotlin.math.round

const val MIN_FREQ = 12f
const val MAX_FREQ = 1218f
const val MIN_TEMP = -40f
const val MAX_TEMP = 120f

const val DIPLEX_FILTER_START = 45 // TODO: should probably be configurable until we are 100% high split

const val TX_LOW_THRESHOLD = 30
const val TX_HIGH_THRESHOLD = 54
const val RX_LOW_THRESHOLD = -10
const val RX_HIGH_THRESHOLD = 11

/*********************************************************************************
 * HomeScreen()
 *      Main screen interface that takes input for frequency, temp, and starting
 *      level as well as shows the main attenuator list along with total
 *      attenuation and ending level
 *********************************************************************************/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: mainViewModel,
) {
    val freqRange = MIN_FREQ..MAX_FREQ
    val tempRange = MIN_TEMP..MAX_TEMP

    var freqSliderPosition by remember { mutableStateOf(mainViewModel.currentFreq) }
    var tempSliderPosition by remember { mutableStateOf(mainViewModel.currentTemp) }
    var startLevel by remember { mutableStateOf(mainViewModel.currentStartLevel) }
    var editLengthDialog by remember { mutableStateOf(false) }
    var editFreqDialog by remember { mutableStateOf(false) }
    var editTempDialog by remember { mutableStateOf(false) }

    // temp card for editing current card
    var editCard by remember { mutableStateOf(AttenuatorCard("", listOf())) }

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

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "${freqSliderPosition.toInt()} MHz")
                    // frequency edit
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit frequency",
                        modifier = Modifier.clickable { editFreqDialog = true }
                    )
                }
            }
            Slider(
                value = freqSliderPosition,
                onValueChange = {
                    freqSliderPosition = it
                },
                valueRange = freqRange,
                steps = 201,
                onValueChangeFinished = {
                    mainViewModel.setFreq(freqSliderPosition)

                    if (mainViewModel.attenuatorCardList.isNotEmpty())
                        mainViewModel.setHasListChanged()
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

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "${tempSliderPosition.toInt()} F")
                    // temp edit
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit frequency",
                        modifier = Modifier.clickable { editTempDialog = true }
                    )
                }
            }
            Slider(
                value = tempSliderPosition,
                onValueChange = {
                    tempSliderPosition = it
                },
                valueRange = tempRange,
                steps = 15,
                onValueChangeFinished = {
                    mainViewModel.setTemp(tempSliderPosition)

                    if (mainViewModel.attenuatorCardList.isNotEmpty())
                        mainViewModel.setHasListChanged()
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
                    text = "@ ${round(mainViewModel.currentFreq).toInt()} MHz",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            OutlinedTextField(
                value = startLevel,
                onValueChange = {
                    val str = it.trim()

                    // if we manually delete all characters, update start level state
                    if (str.isEmpty() || str.isDigitsOnly()) {
                        mainViewModel.setStartLevel(str)
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
                mainViewModel.setStartLevel(startLevel)
        }

        Spacer(modifier = Modifier.padding(6.dp))
//        if (mainViewModel.attenuatorCardList.isNotEmpty()) {
//            // clear icon
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier.background(Color.Transparent)
//            ) {
//                FloatingActionButton(
//                    shape = RoundedCornerShape(50.dp),
//                    onClick = {
//                        mainViewModel.setStartLevel("")
//                        mainViewModel.setTotalAtten(0.0)
//                        mainViewModel.clearAttenuatorList()
//                    },
//                    modifier = Modifier.size(40.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Clear,
//                        contentDescription = "Clear",
//                    )
//                }
//            }
//        }


        // Main attenuation list
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            if (mainViewModel.hasListChanged) {
                mainViewModel.attenuatorCardList.forEach {
                    it?.setLoss(
                        getCableLoss(
                            it.getAttenuator(),
                            mainViewModel.currentFreq.toInt(),
                            it.length(),
                            mainViewModel.currentTemp.toInt()
                        )
                    )

                }
            }

            if (!mainViewModel.attenuatorCardList.isNullOrEmpty()) {
                var total = 0.0

                mainViewModel.attenuatorCardList.forEach {
                    if (it != null) {
                        total += it.getLoss()

                        AddAttenuatorCard(it,
                            onSwipeEdit = {
                                // save the card for editing outside the loop
                                editCard = it
                                editLengthDialog = true
                            },
                            onSwipeDelete = {
                                if (mainViewModel.attenuatorCardList.contains(it)) {
                                    mainViewModel.setTotalAtten(
                                        mainViewModel.totalAttenuation - it.getLoss()
                                    )

                                    mainViewModel.removeAttenuatorCard(it)
                                    mainViewModel.setHasListChanged()
                                }
                            }
                        )
                    }
                }

                mainViewModel.setTotalAtten(total)
            }
            else // if no attenuators, show a message
                Text(text = "Tap to add an attenuator",
                    modifier = Modifier
                        .padding(top = 150.dp)
                        .clickable {
                            mainViewModel.SETBOTTOMINDEX(1)
                            navController.navigate(route = BottomBarScreen.Add.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                )
        }

        Divider()

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(10.dp)
                .weight(2.25f)
        ) {
            // total attenuation
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = "Total Attenuation: ",
                    modifier = Modifier.weight(2f)
                )

                if (mainViewModel.totalAttenuation > 0)
                    Text(text = "-")

                // round loss to nearest tenth
                Text(
                    text = (round(mainViewModel.totalAttenuation * 10) / 10)
                        .toString() + " dB"
                )
            }

            // ending level
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Text(
                    text = "Ending level: ",
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(2f)
                )

                // if we set a start level, do the math and display end result
                if (mainViewModel.currentStartLevel.isNotEmpty()) {
                    var transmit = false
                    var allPlant = true

                    // reverse freqs we add, forward freqs we subtract
                    val result: Double =
                        if (mainViewModel.currentFreq <= DIPLEX_FILTER_START) {
                            transmit = true
                            mainViewModel.currentStartLevel.toDouble() + mainViewModel.totalAttenuation
                        }
                        else
                            mainViewModel.currentStartLevel.toDouble() - mainViewModel.totalAttenuation

                    // is it all plant items?
                    if (mainViewModel.attenuatorCardList.isNotEmpty()) {
                        for (card in mainViewModel.attenuatorCardList.iterator()) {
                            if (card != null && !card.tags().contains(AttenuatorType.PLANT))
                                allPlant = false
                        }
                    }

                    Text(
                        // Round to nearest tenth
                        text = (round(result * 10) / 10).toString() + " dBmV",
                        color = thresholdColor(allPlant, transmit, result),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        // dialog states

        if (editFreqDialog) {
            NumericDialog(
                label = "Edit Frequency",
                range = freqRange,
                allowDecimal = true,
                defaultValue = mainViewModel.currentFreq.toInt().toString(),
                onCancel = { editFreqDialog = false },
                onAdd = {
                    freqSliderPosition = it.toFloat()
                    mainViewModel.setFreq(freqSliderPosition)
                    mainViewModel.setHasListChanged()
                    editFreqDialog = false
                }
            )
        }

        if (editTempDialog) {
            NumericDialog(
                label = "Edit Temperature",
                range = tempRange,
                allowNegative = true,
                defaultValue = mainViewModel.currentTemp.toInt().toString(),
                onCancel = { editTempDialog = false },
                onAdd = {
                    tempSliderPosition = it.toFloat()
                    mainViewModel.setTemp(tempSliderPosition)
                    mainViewModel.setHasListChanged()
                    editTempDialog = false
                }
            )
        }

        if (editLengthDialog && editCard.length() > 0) {
            NumericDialog(
                label = "Edit Length",
                defaultValue = editCard.length().toString(),
                onCancel = { editLengthDialog = false },
                onAdd = {
                    // find card and edit footage
                    for (card in mainViewModel.attenuatorCardList.iterator()) {
                        if (card != null) {
                            if ((card.name() == editCard.name()) &&
                                (card.length() == editCard.length())
                            ) {

                                card.setLength(it.toInt())
                                break
                            }
                        }
                    }

                    // set state to re-draw main list
                    mainViewModel.setHasListChanged()
                    editLengthDialog = false
                }
            )
        }
    }
}

/*********************************************************************************
 * AddAttenuatorCard()
 *      Adds one swipeable card that represents one AttenuatorCard
 *      This gets added to the main attenuator list.
 *********************************************************************************/
@Composable
private fun AddAttenuatorCard(
    data: AttenuatorCard,
    onSwipeEdit: () -> Unit,
    onSwipeDelete: () -> Unit
) {
    val edit = SwipeAction(
        onSwipe = {
            onSwipeEdit()
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.LightGray
            )
        },
        background = LightBlue
    )

    val delete = SwipeAction(
        onSwipe = {
            onSwipeDelete()
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
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
                    .padding(vertical = 10.dp)
            ) {
                // show attenuator name on left
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
                    text = "-" + (round(data.getLoss() * 10) / 10).toString() + "dB",
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

/*********************************************************************************
 * ThresholdColor()
 *      Return the text color that matches the threshold criteria
 *********************************************************************************/
@Composable
private fun thresholdColor(allPlant: Boolean, transmit: Boolean, result: Double): Color {
    if (allPlant)
        return MaterialTheme.colorScheme.primary

    return if (transmit) {
        if ((result >= TX_LOW_THRESHOLD) && (result < TX_HIGH_THRESHOLD))
            LightGreen
        else
            Color.Red
    } else {
        if ((result >= RX_LOW_THRESHOLD) && (result < RX_HIGH_THRESHOLD))
            LightGreen
        else
            Color.Red
    }
}