package com.aaron.cableninja.ui.addscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aaron.cableninja.ui.MainActivity.Companion.attenuatorCardList
import com.aaron.cableninja.ui.MainActivity.Companion.attenuatorMap
import com.aaron.cableninja.ui.MainActivity.Companion.attenuatorTags
import com.aaron.cableninja.R
import com.aaron.cableninja.ui.viewmodels.SharedViewModel
import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.data.AttenuatorTag
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.data.getCableLoss
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.ui.dialogs.LengthDialog
import com.aaron.cableninja.ui.navigation.Screen
import com.aaron.cableninja.ui.theme.coaxColor
import com.aaron.cableninja.ui.theme.dropColor
import com.aaron.cableninja.ui.theme.passiveColor
import com.aaron.cableninja.ui.theme.plantColor


/**************************************************************
 * AddScreen()
 *      Show attenuators that can be added.
 **************************************************************/
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {
    var showLengthDialog by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var executeSearch by remember { mutableStateOf(false) }
    var coaxFilter by remember { mutableStateOf(false) }
    var passiveFilter by remember { mutableStateOf(false) }
    var dropFilter by remember { mutableStateOf(false) }
    var plantFilter by remember { mutableStateOf(false) }

    // The list we will show based on filters (or all if no filters)
    var showList = mutableListOf<Attenuator>()

    val kbController = LocalSoftwareKeyboardController.current

    Column {
        // Add Header with Close Icon "X" on right side
        //      cancels Add operation
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            // Add Label
            Text(
                text = "Add attenuator",
                fontWeight = FontWeight.Bold
            )

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

        // search box
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it.trim()
                },
                singleLine = true,
                modifier = Modifier
                    .weight(.6f)
                    .padding(start = 10.dp, end = 20.dp)
            )
            Button(
                onClick = {
                    if (!search.isNullOrEmpty()) {
                        Log.d("DEBUG", "AddScreen(): executing search for $search")

                        // hide keyboard
                        kbController?.hide()
                        executeSearch = true
                    }
                },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .weight(.4f)
                    .padding(end = 20.dp)
            ) {
                Text(text = "Search")
            }
        }

        // Filter by tags
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = "Filter: ",
                modifier = Modifier.padding(start = 10.dp)
            )

            // show all possible tags
            attenuatorTags.forEach {
                if (!coaxFilter && !passiveFilter && !dropFilter && !plantFilter) {
                    Log.d("DEBUG", "AddScreen() no filters selected")

                    AddTag(
                        tag = AttenuatorTag(it.key),
                        color = Color.LightGray,
                        style = MaterialTheme.typography.titleMedium,
                        onClick = { t ->
                            if (t.tag == AttenuatorType.COAX)
                                coaxFilter = true
                            if (t.tag == AttenuatorType.PASSIVE)
                                passiveFilter = true
                            if (t.tag == AttenuatorType.DROP)
                                dropFilter = true
                            if (t.tag == AttenuatorType.PLANT)
                                plantFilter = true

                            Log.d("DEBUG", "AddScreen() filter selected: $t")
                        }
                    )
                }

                // coax filter
                if (coaxFilter && it.key == AttenuatorType.COAX) {
                    Log.d("DEBUG", "AddScreen() coaxFilter = true")
                    AddFilter(AttenuatorType.COAX, onClearFilter = { coaxFilter = false })
                }
                // passive filter
                if (passiveFilter && it.key == AttenuatorType.PASSIVE) {
                    Log.d("DEBUG", "AddScreen() passiveFilter = true")
                    AddFilter(AttenuatorType.PASSIVE, onClearFilter = { passiveFilter = false })
                }
                // drop filter
                if (dropFilter && it.key == AttenuatorType.DROP) {
                    Log.d("DEBUG", "AddScreen() dropFilter = true")
                    AddFilter(AttenuatorType.DROP, onClearFilter = { dropFilter = false })
                }
                // plant filter
                if (plantFilter && it.key == AttenuatorType.PLANT) {
                    Log.d("DEBUG", "AddScreen() plantFilter = true")
                    AddFilter(AttenuatorType.PLANT, onClearFilter = { plantFilter = false })
                }
            }
        }

        val noTags = (!coaxFilter && !passiveFilter && !dropFilter && !plantFilter)
        // if no tags and no search query, just copy the whole list
        if (noTags && !executeSearch)
            showList = attenuatorMap.values.toMutableList()
        else {
            // iterate over all possible attenuators and add to showList
            // the ones that match search filters
            for (att in attenuatorMap.values) {
                // execute search query
                if (noTags && executeSearch) {
                    if (att.name().contains(search, ignoreCase = true)) {
                        showList.add(att)
                        // if no tags, no point in going any further
                        continue
                    }
                }

                // check if attenuator meets tag filter
                if ((coaxFilter && att.isCoax()) ||
                    (passiveFilter && att.isPassive()) ||
                    (dropFilter && att.isDrop()) ||
                    (plantFilter && att.isPlant()))
                {
                    if (!executeSearch ||
                        (executeSearch && att.name().contains(search, ignoreCase = true)))

                        showList.add(att)
                }
            }
        }

        // display the list
        LazyColumn(
            contentPadding = PaddingValues(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = showList) { attenuator ->
                val card = AttenuatorCard(
                    attenuator.name(),
                    attenuator.tags(),
                    attenuator.isCoax()
                )

                AddAttenuatorCard(
                    card,
                    onClick = {
                        sharedViewModel.setAttenuatorCard(card)

                        // only show footage dialog if we are adding a coax attenuator
                        if (card.isCoax()) {
                            // logic for coax is in this function below in
                            // showLengthDialog conditional
                            showLengthDialog = true
                        } else {
                            // Find loss
                            for (data in attenuatorMap.values) {
                                if (data.name() == card.name()) {
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
            label = "Enter length",
            defaultValue = "",
            onCancel = { showLengthDialog = false },
            onAdd = {
                // Save length to current AttenuatorCard to add to MainScreen list
                sharedViewModel.addAttenuatorLength(it.toInt())

                // Find loss
                for (data in attenuatorMap.values) {
                    if (data.name() == sharedViewModel.card!!.name()) {
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
                        sharedViewModel.card!!.name(),
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
 * AddAttenuatorCard()
 *      Create Card for attenuator type
 ************************************************/
@Composable
private fun AddAttenuatorCard(
    card: AttenuatorCard,
    onClick:() -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.clickable(onClick = { onClick() })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            // Name
            Text(
                text = card.name(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp, bottom = 6.dp, start = 1.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Tags
                card.tags().forEach {
                    val color =
                        when (it.tag) {
                            AttenuatorType.COAX -> coaxColor
                            AttenuatorType.PASSIVE -> passiveColor
                            AttenuatorType.DROP -> dropColor
                            AttenuatorType.PLANT -> plantColor
                        }
                    AddAttenuatorTag(it, color, MaterialTheme.typography.bodySmall)
                }

            }        }
    }
}

/************************************************
 * AddAttenuatorTag()
 *      Create small tags to describe each
 *      attenuator type.  Used for filtering
 *      add list.
 ************************************************/
@Composable
private fun AddAttenuatorTag(
    tag: AttenuatorTag,
    color: Color,
    style: TextStyle,
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
    ) {
        Text(
            text = tag.toString(),
            color = Color.White,
            style = style,
            modifier = Modifier
                .background(color)
                .padding(top = 0.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)
        )
    }
}

@Composable
private fun AddFilter(type: AttenuatorType, onClearFilter: () -> Unit) {
    val color = attenuatorTags[type]

    if (color != null) {
        AddTag(
            tag = AttenuatorTag(type),
            color = color,
            style = MaterialTheme.typography.bodyLarge,
            onClick = {}
        )
        Icon(
            painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "Clear filter",
            modifier = Modifier.clickable { onClearFilter() }
        )
    }
}

@Composable
private fun AddTag(
    tag: AttenuatorTag,
    color: Color,
    style: TextStyle,
    onClick: (AttenuatorTag) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = tag.toString(),
            color = Color.White,
            style = style,
            modifier = Modifier
                .background(color)
                .padding(top = 0.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)
                .clickable {
                    onClick(tag)
                }
        )
    }
}

