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
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aaron.cableninja.ui.MainActivity.Companion.attenuatorCardList
import com.aaron.cableninja.ui.MainActivity.Companion.attenuatorMap
import com.aaron.cableninja.ui.MainActivity.Companion.attenuatorTags
import com.aaron.cableninja.R
import com.aaron.cableninja.ui.SharedViewModel
import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.domain.AttenuatorTag
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.domain.getCableLoss
import com.aaron.cableninja.domain.AttenuatorCard
import com.aaron.cableninja.ui.LengthDialog
import com.aaron.cableninja.ui.navigation.Screen
import com.aaron.cableninja.ui.theme.coaxColor
import com.aaron.cableninja.ui.theme.dropColor
import com.aaron.cableninja.ui.theme.passiveColor
import com.aaron.cableninja.ui.theme.plantColor


/**************************************************************
 * AddScreen()
 *      Show attenuators that can be added.
 **************************************************************/
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

    // The list we will show based on filters (or all if no filters)
    val showList = mutableListOf<Attenuator>()

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
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(
                text = "Filter: ",
                modifier = Modifier.padding(start = 10.dp)
            )

            // show all possible tags
            attenuatorTags.forEach {
                // if no filters
                if (!coaxFilter && !passiveFilter && !dropFilter && !plantFilter) {
                    Log.d("DEBUG", "AddScreen() no filters selected")

                    AddTagFilter(
                        tag = AttenuatorTag(it.key),
                        color = Color.LightGray,
                        style = MaterialTheme.typography.titleMedium,
                        onClick = {
                            if (it.tag == AttenuatorType.COAX)
                                coaxFilter = true
                            if (it.tag == AttenuatorType.PASSIVE)
                                passiveFilter = true
                            if (it.tag == AttenuatorType.DROP)
                                dropFilter = true
                            if (it.tag == AttenuatorType.PLANT)
                                plantFilter = true

                            Log.d("DEBUG", "AddScreen() filter selected: $it")
                            // TODO only show types that match tags selected
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

        // iterate over all possible attenuators and add to showList the
        // ones we want to display
        attenuatorMap.values.forEach { type ->
            if (!coaxFilter && !passiveFilter && !dropFilter && !plantFilter)
                showList.add(type)

            if (coaxFilter && type.isCoax())
                showList.add(type)
            if (passiveFilter && type.isPassive())
                showList.add(type)
            if (dropFilter && type.isDrop())
                showList.add(type)
            if (plantFilter && type.isPlant())
                showList.add(type)
        }

        // display the list
        LazyColumn(
            contentPadding = PaddingValues(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = showList) { attenuator ->
                Log.d(
                    "DEBUG",
                    "AddScreen(): hasLoadedAddList = ${sharedViewModel.hasLoadedAddList}"
                )

                val card = AttenuatorCard(
                    attenuator.name(),
                    attenuator.tags(),
                    attenuator.isCoax()
                )

                Log.d(
                    "DEBUG", "AddScreen(): showList.forEach() \n" +
                            "    ${attenuator.name()}\n" +
                            "    ${attenuator.tagsToStrings()}\n" +
                            "    ${attenuator.isCoax()}"
                )

                AddAttenuatorCard(
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
                                if (data.name() == card.id()) {
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

    sharedViewModel.setHasLoadedAddList()

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
                    if (data.name() == sharedViewModel.card!!.id()) {
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
            // TODO add image of coax or splitter

            // ID
            Text(
                text = card.id(),
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
        AddTagFilter(
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
private fun AddTagFilter(
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

