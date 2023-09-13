package com.aaron.cableninja.ui.addscreen

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.aaron.cableninja.ui.viewmodels.mainViewModel
import com.aaron.cableninja.data.Attenuator
import com.aaron.cableninja.data.AttenuatorType
import com.aaron.cableninja.data.getCableLoss
import com.aaron.cableninja.data.AttenuatorCard
import com.aaron.cableninja.ui.dialogs.NumericDialog
import com.aaron.cableninja.ui.navigation.BottomBarScreen
import com.aaron.cableninja.ui.theme.coaxColor
import com.aaron.cableninja.ui.theme.dropColor
import com.aaron.cableninja.ui.theme.passiveColor
import com.aaron.cableninja.ui.theme.plantColor


/*********************************************************************************
 * AddScreen()
 *      Show attenuators that can be added.
 *********************************************************************************/
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddScreen(
    navController: NavController,
    mainViewModel: mainViewModel,
) {
    val showList = mutableStateListOf<Attenuator>()
    var showLengthDialog by remember { mutableStateOf(false) }

    // search/filter states
    var search by remember { mutableStateOf("") }
    var coaxFilter by remember { mutableStateOf(false) }
    var passiveFilter by remember { mutableStateOf(false) }
    var dropFilter by remember { mutableStateOf(false) }
    var plantFilter by remember { mutableStateOf(false) }

    // allows us to close keyboard when we want
    val kbController = LocalSoftwareKeyboardController.current

    Column {
        // search box
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it.trim()
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Perform search",
                    )
                },
                trailingIcon = {
                    if (search.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                            modifier = Modifier.clickable {
                                search = ""
                                kbController?.hide()
                            }
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier
                    .weight(.6f)
                    .padding(start = 10.dp, end = 10.dp)
            )
        }

        // filters
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filter:",
                modifier = Modifier.padding(start = 12.dp, top=2.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 6.dp, end = 20.dp)
            ) {
                // show all possible tags
                if (mainViewModel.filterList.isEmpty()) {
                    mainViewModel.attenuatorTags.keys.forEach {
                        mainViewModel.attenuatorTags[it]?.let { color ->
                            AddFilter(
                                filterEnabled = false,
                                filterColor = color,
                                type = it,
                                onClick = { type ->
                                    mainViewModel.addFilter(type)

                                    when (type) {
                                        AttenuatorType.COAX -> coaxFilter = true
                                        AttenuatorType.PASSIVE -> passiveFilter = true
                                        AttenuatorType.DROP -> dropFilter = true
                                        AttenuatorType.PLANT -> plantFilter = true
                                    }
                                }
                            )
                        }
                    }
                }
                // otherwise show filters
                else {
                    // coax filter
                    mainViewModel.attenuatorTags[AttenuatorType.COAX]?.let {
                        AddFilter(
                            filterEnabled = coaxFilter,
                            filterColor = it,
                            type = AttenuatorType.COAX,
                            onClick = {
                                if (coaxFilter)
                                    mainViewModel.removeFilter(it)
                                else
                                    mainViewModel.addFilter(it)

                                coaxFilter = !coaxFilter
                            }
                        )
                    }

                    // passive filter
                    mainViewModel.attenuatorTags[AttenuatorType.PASSIVE]?.let {
                        AddFilter(
                            filterEnabled = passiveFilter,
                            filterColor = it,
                            type = AttenuatorType.PASSIVE,
                            onClick = {
                                if (passiveFilter)
                                    mainViewModel.removeFilter(it)
                                else
                                    mainViewModel.addFilter(it)

                                passiveFilter = !passiveFilter
                            }
                        )
                    }

                    // drop filter
                    mainViewModel.attenuatorTags[AttenuatorType.DROP]?.let {
                        AddFilter(
                            filterEnabled = dropFilter,
                            filterColor = it,
                            type = AttenuatorType.DROP,
                            onClick = {
                                if (dropFilter)
                                    mainViewModel.removeFilter(it)
                                else
                                    mainViewModel.addFilter(it)

                                dropFilter = !dropFilter
                            }
                        )
                    }

                    // plant filter
                    mainViewModel.attenuatorTags[AttenuatorType.PLANT]?.let {
                        AddFilter(
                            filterEnabled = plantFilter,
                            filterColor = it,
                            type = AttenuatorType.PLANT,
                            onClick = {
                                if (plantFilter)
                                    mainViewModel.removeFilter(it)
                                else
                                    mainViewModel.addFilter(it)

                                plantFilter = !plantFilter
                            }
                        )
                    }
                }
            }
        }

        // build list of items to show, based on search and filters
        for (attenuator in mainViewModel.attenuatorList.iterator()) {
            if (showList.contains(attenuator))
                continue

            if (attenuator.doesMatchSearch(search, mainViewModel.filterList))
                showList.add(attenuator)
        }

        LazyColumn(
            contentPadding = PaddingValues(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = showList) { attenuator ->
                val card = AttenuatorCard(attenuator)

                AddAttenuatorCard(
                    card,
                    onClick = {
                        // only show footage dialog if we are adding a coax attenuator
                        if (card.isCoax()) {
                            // logic for coax is in this function below in
                            // showLengthDialog conditional
                            mainViewModel.setAttenuatorCard(card)
                            showLengthDialog = true
                        } else {
                            // Find passive loss
                            for (data in mainViewModel.attenuatorList.iterator()) {
                                if (data.name() == card.name()) {
                                    card.setLoss(
                                        getCableLoss(
                                            data,
                                            mainViewModel.currentFreq.toInt(),
                                            0,
                                            mainViewModel.currentTemp.toInt()
                                        )
                                    )

                                    break
                                }
                            }

                            // nav back to MainScreen
                            mainViewModel.addAttenuatorCard(card)
                            mainViewModel.SETBOTTOMINDEX(0)
                            navController.navigate(BottomBarScreen.Home.route)
                            {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }

    // Show footage dialog when adding coax attenuators
    if (showLengthDialog) {
        val card = mainViewModel.getCurrentCard()

        NumericDialog(
            label = "Enter length",
            defaultValue = "",
            onCancel = { showLengthDialog = false },
            onAdd = {
                if (card != null) {
                    card.setLength(it.toInt())

                    // Find loss
                    for (data in mainViewModel.attenuatorList.iterator()) {
                        if (data.name() == card.name()) {
                            card.setLoss(
                                getCableLoss(
                                    data,
                                    mainViewModel.currentFreq.toInt(),
                                    card.length(),
                                    mainViewModel.currentTemp.toInt()
                                )
                            )

                            break
                        }
                    }

                    mainViewModel.addAttenuatorCard(card)

                    // Nav back to MainScreen
                    navController.navigate(BottomBarScreen.Home.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                    mainViewModel.SETBOTTOMINDEX(0)
                }
            }
        )
    }
}

/**************************************************************************
 * AddAttenuatorCard()
 *      Create Card for attenuator type
 **************************************************************************/
@Composable
private fun AddAttenuatorCard(
    card: AttenuatorCard,
    onClick:() -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .clickable(onClick = { onClick() })
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
                        when (it) {
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

/**************************************************************************
 * AddAttenuatorTag()
 *      Create small tags to describe each attenuator type.  This
 *      goes on each individual attenuator card (as opposed to the
 *      filter cards)
 **************************************************************************/
@Composable
private fun AddAttenuatorTag(
    type: AttenuatorType,
    color: Color,
    style: TextStyle,
) {
    Surface(
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = type.toString(),
            color = Color.White,
            style = style,
            modifier = Modifier
                .background(color)
                .padding(top = 0.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)
        )
    }
}

/**************************************************************************
 * AddTag()
 *      Add filter card.  Automatically colors card bgcolor to show
 *      whether it has been enabled or disabled.
 **************************************************************************/
@Composable
private fun AddFilter(
    filterEnabled: Boolean,
    filterColor: Color,
    type: AttenuatorType,
    onClick: (AttenuatorType) -> Unit
) {
    var tagBackgroundColor = Color.LightGray
    val tagFontStyle = MaterialTheme.typography.titleMedium

    if (filterEnabled)
        tagBackgroundColor = filterColor

    Surface(
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = type.toString(),
            color = Color.White,
            style = tagFontStyle,
            modifier = Modifier
                .background(tagBackgroundColor)
                .padding(top = 0.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)
                .clickable {
                    onClick(type)
                }
        )
    }
}

