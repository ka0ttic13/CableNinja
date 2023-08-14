package com.aaron.cableninja.presentation.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.aaron.cableninja.MainActivity.Companion.attenuatorCardList
import com.aaron.cableninja.MainActivity.Companion.manufacturerSpecsMap
import com.aaron.cableninja.R
import com.aaron.cableninja.domain.AttenuatorCard
import com.aaron.cableninja.domain.getCableLoss

/*
 * AddScreen()
 *      Show attenuators that can be added.
 */
@Composable
fun AddScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {
    var showLengthDialog by remember { mutableStateOf(false) }

    Column {
        // Add Header with Close Icon "X" on right side
        //      cancels Add operation
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Add Label
            Text(text = "Add attenuator")

            // Close Icon - exits back to MainScreen
            Icon(
                painterResource(id = R.drawable.baseline_close_24),
                contentDescription = "Close",
                modifier = Modifier.clickable {
                    // on click, go back to MainScreen()
                    navController.popBackStack()
                }
            )
        }

        // Show attenuator types that can be added
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Log.d("DEBUG", "AddScreen(): hasLoadedAddList = ${sharedViewModel.hasLoadedAddList}")

            // iterate over attenuatorList and create an AttenuatorAddCard for
            // each Attenuator in the list
            for (type in manufacturerSpecsMap.values) {
                val card = AttenuatorCard(type.id(), type.desc(), 0, type.iscoax())
                AttenuatorAddCard(
                    card,
                    onClick = {
                        if (sharedViewModel.clearAttenuatorList)
                            sharedViewModel.setClearListFalse()

                        sharedViewModel.setAttenuatorCard(card)

                        // only show footage dialog if we are adding a coax attenuator
                        if (card.iscoax()) {
                            // logic for coax is in AddScreen under footage dialog state
                            showLengthDialog = true
                        } else {
                            // Find loss
                            for (data in manufacturerSpecsMap.values) {
                                if (data.id() == card.id()) {
                                    sharedViewModel.card!!.setLoss(
                                        getCableLoss(
                                            data,
                                            sharedViewModel.currentFreq.toInt(),
                                            0,
                                            sharedViewModel.currentTemp.toInt()
                                        )
                                    )

                                    break
                                }
                            }

                            // nav back to MainScreen
                            attenuatorCardList.add(card)
                            navController.navigate(Screen.Main.route)
                        }
                    }
                )
            }
        }
    }

    // Show footage dialog when adding coax attenuators
    if (showLengthDialog) {
        Log.d("DEBUG", "Entering showFootageDialog")
        LengthDialog(
            onCancel = { showLengthDialog = false },
            onAdd = {
                Log.d(
                    "DEBUG",
                    "Footage dialog: Adding " + sharedViewModel.card!!.id()
                )
                Log.d("DEBUG", "with footage = ${sharedViewModel.getAttenuatorLength()}")

                // Save length to current AttenuatorCard to add to MainScreen list
                sharedViewModel.addAttenuatorLength(it.toInt())

                // Find loss
                for (data in manufacturerSpecsMap.values) {
                    if (data.id() == sharedViewModel.card!!.id()) {
                        Log.d(
                            "DEBUG",
                            "Footage dialog: Found attenuator ${data.id()}"
                        )
                        Log.d(
                            "DEBUG",
                            "Footage dialog: current freq " + sharedViewModel.currentFreq.toString()
                        )
                        Log.d(
                            "DEBUG",
                            "Footage dialog: current temp " + sharedViewModel.currentTemp.toString()
                        )

                        sharedViewModel.card!!.setLoss(
                            getCableLoss(
                                data,
                                sharedViewModel.currentFreq.toInt(),
                                sharedViewModel.getAttenuatorLength(),
                                sharedViewModel.currentTemp.toInt()
                            )
                        )

                        break
                    }
                }

                attenuatorCardList.add(
                    AttenuatorCard(
                        sharedViewModel.card!!.id(),
                        sharedViewModel.card!!.desc(),
                        sharedViewModel.card!!.footage(),
                        sharedViewModel.card!!.iscoax(),
                        sharedViewModel.card!!.getLoss()
                    )
                )

                // Nav back to MainScreen
                navController.navigate(Screen.Main.route)
            }
        )
    }
}

/*
 *
 * AttenuatorAddCard()
 *      Create Card for attenuator type
 */

@Composable
private fun AttenuatorAddCard(
    card: AttenuatorCard,
    onClick:() -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.clickable(onClick = { onClick() })
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            // TODO add image of coax or splitter
            Text(
                text = card.id(),
                fontWeight = FontWeight.Bold
            )
            Text(text = card.desc())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LengthDialog(
    onCancel: () -> Unit,
    onAdd: (String) -> Unit) {
    var openDialog by remember { mutableStateOf(true) }
    var showFootageAlert by remember { mutableStateOf(false) }

    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialog = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(0.95f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    var length by remember { mutableStateOf("") }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextField(
                            value = length,
                            onValueChange = {
                                length = it
                            },
                            label = { Text(text = "Enter length") },
                            singleLine = true,
                            // show number pad for input
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cancel Button
                        Button(
                            onClick = {
                                openDialog = false
                                onCancel()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "Cancel",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Add Button
                        Button(
                            onClick = {
                                // validate input
                                if (length.isEmpty() || !isNumeric(length))
                                    showFootageAlert = true
                                else
                                    onAdd(length)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "Add",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    // Show AlertDialog if footage entered is not a number
    if (showFootageAlert)
        FootageAlertDialog()
}

@Composable
private fun FootageAlertDialog() {
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {},
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            tonalElevation = 10.dp,
            confirmButton = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    TextButton(
                        onClick = { openDialog = false }
                    ) {
                        Text(
                            text = "OK",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Footage must be a positive number"
                    )
                }
            }
        )
    }
}

// Return true if string is numeric
private fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}