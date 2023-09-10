package com.aaron.cableninja.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import com.aaron.cableninja.data.isNumeric


/*******************************************************************
 * NumericDialog()
 *      Create Dialog for user to enter numeric input
 *******************************************************************/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumericDialog(
    label: String,
    defaultValue: String,
    onCancel: () -> Unit,
    onAdd: (String) -> Unit,
    range: ClosedFloatingPointRange<Float>? = null,
    allowNegative: Boolean = false,
    allowDecimal: Boolean = false
    ) {
    var showDialog by remember { mutableStateOf(true) }
    var showAlertDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
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
                    var value by remember { mutableStateOf(defaultValue) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedTextField(
                            value = value,
                            onValueChange = {
//                                val str = it.trim()
//                                if (str.isDigitsOnly())
//                                    value = str

                                val str = it.trim()

                                if (str.isDigitsOnly())
                                    value = str
                                else if (allowNegative && str.startsWith("-"))
                                    value = str
                                else if (allowDecimal && str.contains(char = '.'))
                                    value = str
                            },
                            label = {
                                Text(
                                    text = label,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            singleLine = true,
                            // show number pad for input
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }

                    // Button Row
                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cancel Button
                        Button(
                            onClick = {
                                showDialog = false
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
                                if (value.isEmpty() || !isNumeric(value))
                                    showAlertDialog = true
                                else if (!allowDecimal && value.contains('.'))
                                    showAlertDialog = true
                                else if (!allowNegative && value.startsWith("-"))
                                    showAlertDialog = true
                                else if (range != null && value.toDouble() !in range)
                                    showAlertDialog = true
                                else
                                    onAdd(value)
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
    if (showAlertDialog)
        NumericAlertDialog(range)
}

/************************************************************************
 * NumericAlertDialog()
 *      Create AlertDialog for invalid user input from NumericDialog
 ************************************************************************/
@Composable
private fun NumericAlertDialog(range: ClosedFloatingPointRange<Float>?) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
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
                        onClick = { showDialog = false }
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
                    if (range != null)
                        Text(text = "Number is out of range!")
                    else
                        Text(text = "Must be a positive whole number!"
                    )
                }
            }
        )
    }
}
