package com.aaron.cableninja.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.aaron.cableninja.MainActivity.Companion.attenuatorCardList
import com.aaron.cableninja.MainActivity.Companion.attenuatorMap
import com.aaron.cableninja.MainActivity.Companion.attenuatorTags
import com.aaron.cableninja.R
import com.aaron.cableninja.domain.AttenuatorTag
import com.aaron.cableninja.domain.AttenuatorType
import com.aaron.cableninja.domain.getCableLoss
import com.aaron.cableninja.presentation.ui.theme.LightBlue
import com.aaron.cableninja.presentation.ui.theme.LightGreen
import com.aaron.cableninja.presentation.ui.theme.LightRed
import com.aaron.cableninja.presentation.ui.theme.LightYellow

/***
 * AddScreen()
 *      Show attenuators that can be added.
 ***/
@Composable
fun AddScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {
    var showLengthDialog by remember { mutableStateOf(false) }
    var coaxFilter by remember { mutableStateOf(false) }
    var passiveFilter by remember { mutableStateOf(false) }
    var dropFilter by remember { mutableStateOf(false) }
    var plantFilter by remember { mutableStateOf(false) }

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

        Divider(modifier = Modifier.padding(bottom = 6.dp))

        // Filter by tags
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = "Tag Filters: ",
                modifier = Modifier.padding(start = 10.dp))

            // show all possible tags with no color
            attenuatorTags.forEach {
                attenuatorTags[it.key]?.let { it1 ->
                    AddAttenuatorTag(
                        tag = it.key,
                        color = Color.LightGray,
                        clickColor = it1,
                        onClick = {
                            if (it.tag == AttenuatorType.COAX)
                                coaxFilter = true
                            else if (it.tag == AttenuatorType.PASSIVE)
                                passiveFilter = true
                            else if (it.tag == AttenuatorType.DROP)
                                dropFilter = true
                            else if (it.tag == AttenuatorType.PLANT)
                                plantFilter = true

                            Log.d("DEBUG", "AddScreen() filter selected: $it")
                            // TODO only show types that match tags selected
                        }
                    )
                }
            }
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
            Log.d("DEBUG",
                "AddScreen(): hasLoadedAddList = ${sharedViewModel.hasLoadedAddList}")

            // TODO iterator over tags, if any.  If not, show all tags.


            // iterate over attenuatorList and create an AttenuatorAddCard for
            // each Attenuator in the list
            for (type in attenuatorMap.values) {
                val card = AttenuatorCard(type.id(), type.tags(), type.isCoax())
                AttenuatorAddCard(
                    card,
                    onClick = {
                        // since we are adding, reset clear list state
                        if (sharedViewModel.clearAttenuatorList)
                            sharedViewModel.setClearList(false)

                        sharedViewModel.setAttenuatorCard(card)

                        // only show footage dialog if we are adding a coax attenuator
                        if (card.isCoax()) {
                            // logic for coax is in AddScreen under footage dialog state conditional
                            showLengthDialog = true
                        } else {
                            // Find loss
                            for (data in attenuatorMap.values) {
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
                // Save length to current AttenuatorCard to add to MainScreen list
                sharedViewModel.addAttenuatorLength(it.toInt())

                // Find loss
                for (data in attenuatorMap.values) {
                    if (data.id() == sharedViewModel.card!!.id()) {
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
                        sharedViewModel.card!!.tags(),
                        sharedViewModel.card!!.isCoax(),
                        sharedViewModel.card!!.length(),
                        sharedViewModel.card!!.getLoss()
                    )
                )

                // Nav back to MainScreen
                navController.navigate(Screen.Main.route)
            }
        )
    }
}

/************************************************
 * AttenuatorAddCard()
 *      Create Card for attenuator type
 ************************************************/
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

            // ID
            Text(
                text = card.id(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp, bottom = 6.dp, start = 1.dp)
            )

            // Tags
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Tags:", style = MaterialTheme.typography.bodyMedium)
                // create tags
                card.tags().forEach {
                    val color =
                        when (it.tag) {
                            AttenuatorType.COAX     -> LightBlue
                            AttenuatorType.PASSIVE  -> LightGreen
                            AttenuatorType.DROP     -> LightRed
                            AttenuatorType.PLANT    -> LightYellow
                        }
                    AddAttenuatorTag(it, color) {
                        /* no onClick() wanted here so pass an empty lambda */
                    }
                }
            }
        }
    }
}

/************************************************
 * LengthDialog()
 *      Create Dialog for user to enter
 *      attenuator length
 *      Note: not declared private as we use the
 *      same dialog to edit length in MainScreen()
 ************************************************/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LengthDialog(
    onCancel: () -> Unit,
    onAdd: (String) -> Unit) {
    var openDialog by remember { mutableStateOf(true) }
    var showLengthAlert by remember { mutableStateOf(false) }

    if (openDialog) {
        Log.d("DEBUG", "LengthDialog() called")

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
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
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
                                if (length.isEmpty() || !isNumeric(length)) {
                                    Log.d("DEBUG", "LengthDialog(): \"$length\" is not valid input")
                                    showLengthAlert = true
                                }
                                else {
                                    Log.d("DEBUG", "LengthDialog() entered length: $length")
                                    onAdd(length)
                                }
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
    if (showLengthAlert)
        LengthAlertDialog()
}

/************************************************
 * LengthAlertDialog()
 *      Create AlertDialog for invalid user
 *      input from LengthDialog
 ************************************************/
@Composable
private fun LengthAlertDialog() {
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
                        text = "Footage must be a positive whole number"
                    )
                }
            }
        )
    }
}

/************************************************
 * AttenuatorTags()
 *      Create small tags to describe each
 *      attenuator type.  Used for filtering
 *      add list.
 ************************************************/
@Composable
private fun AddAttenuatorTag(
    tag: AttenuatorTag,
    color: Color,
    clickColor: Color = Color.Transparent,
    onClick: (AttenuatorTag) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
    ) {
        Text(
            text = tag.toString(),
            color = Color.White,
            modifier = Modifier
                .background(
                    color
                )
                .padding(top = 0.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)
                .clickable {
                    onClick(tag)
                }
        )
    }
}

// Return true if string is numeric
private fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}