package com.aaron.cableninja.screen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.aaron.cableninja.MainActivity.Companion.attenuatorCardList
import com.aaron.cableninja.MainActivity.Companion.attenuatorList
import com.aaron.cableninja.R

/*
 *
 * AddScreen()
 *      Show attenuators that can be added.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {
    // Add Label with Close Icon "X"
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
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
            .padding(top = 60.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        var openFootageAlert by remember { mutableStateOf(false) }

        // iterate over attenuatorList and create an AttenuatorAddCard for
        // each Attenuator in the list
        attenuatorList.forEach() {
            AttenuatorAddCard(
                AttenuatorCardData(
                    it.id(), it.desc(),
                    0, it.iscoax()
                ), navController, sharedViewModel
            )
        }

        // Show footage dialog when adding coax attenuators
        if (sharedViewModel.isFootageDialogShown) {
            Dialog(
                onDismissRequest = {
                    sharedViewModel.onFootageDismissDialog()
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
                        var footage by remember { mutableStateOf("") }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextField(
                                value = footage,
                                onValueChange = {
                                    footage = it
                                },
                                label = { Text(text = "Enter footage") },

                                singleLine = true
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Cancel Button
                            Button(
                                onClick = {
                                    sharedViewModel.onFootageDismissDialog()
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
                                    sharedViewModel.onFootageAddClick()

                                    if (isNumeric(footage) &&
                                        sharedViewModel.card != null) {
                                            // Save length to current AttenuatorCard to add to MainScreen list
                                            sharedViewModel.addAttenuatorLength(footage.toInt())
                                            // Nav back to MainScreen
                                            navController.navigate(Screen.Main.route)
                                    }
                                    else
                                        openFootageAlert = true
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
        if (openFootageAlert)
            FootageAlertDialog()
    }
}

/*
 *
 * AttenuatorAddCard()
 *      Create Card for attenuator type
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AttenuatorAddCard(
    card: AttenuatorCardData,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    Log.d("DEBUG", "Entering AttenuatorAddCard() with id = " + card.id)
    Log.d("DEBUG", "Entering AttenuatorAddCard() with desc = " + card.desc)
    Log.d("DEBUG", "=================================================")

    Card(
        shape = RoundedCornerShape(32.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    if (sharedViewModel.clearAttenuatorList)
                        sharedViewModel.setClearListFalse()

                    sharedViewModel.addAttenuatorCard(card)

                    attenuatorCardList.add(AttenuatorCard(card))

                    // only show footage dialog if we are adding a coax attenuator
                    if (card.iscoax)
                        sharedViewModel.onFootageAddClick()
                    else
                        navController.navigate(Screen.Main.route)
                }
                .padding(36.dp)
                .fillMaxWidth()
        ) {
            // TODO add image of coax or splitter
            Text(
                text = card.id,
                fontWeight = FontWeight.Bold
            )
            Text(text = card.desc)
        }
    }
}

@Composable
private fun FootageAlertDialog() {
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = { openDialog = false }
                ) {
                    Text(text = "Ok")
                }
            },
            text = { Text(text = "Footage must be a number") }
        )
    }
}

// Return true if string is numeric
private fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}